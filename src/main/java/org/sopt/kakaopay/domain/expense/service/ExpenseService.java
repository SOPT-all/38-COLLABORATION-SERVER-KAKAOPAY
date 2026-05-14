package org.sopt.kakaopay.domain.expense.service;

import lombok.RequiredArgsConstructor;
import org.sopt.kakaopay.domain.expense.code.ExpenseErrorCode;
import org.sopt.kakaopay.domain.expense.dto.ExpenseCategoryAmountDto;
import org.sopt.kakaopay.domain.expense.dto.response.DailyTransactionResponse;
import org.sopt.kakaopay.domain.expense.dto.response.ExpenseAnalysisResponse;
import org.sopt.kakaopay.domain.expense.dto.response.ExpenseCategoryResponse;
import org.sopt.kakaopay.domain.expense.dto.response.ExpenseDetailResponse;
import org.sopt.kakaopay.domain.expense.entity.Payment;
import org.sopt.kakaopay.domain.expense.entity.SplitPay;
import org.sopt.kakaopay.domain.expense.entity.Transaction;
import org.sopt.kakaopay.domain.expense.dto.response.ExpenseResponse;
import org.sopt.kakaopay.domain.expense.dto.response.TransactionDetailResponse;
import org.sopt.kakaopay.domain.expense.entity.Transfer;
import org.sopt.kakaopay.domain.expense.enums.PaymentCategory;
import org.sopt.kakaopay.domain.expense.enums.TransactionType;
import org.sopt.kakaopay.domain.expense.repository.PaymentRepository;
import org.sopt.kakaopay.domain.expense.repository.SplitPayRepository;
import org.sopt.kakaopay.domain.expense.repository.TransactionRepository;
import org.sopt.kakaopay.global.exception.BusinessException;
import org.sopt.kakaopay.domain.expense.repository.TransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpenseService {
    private final PaymentRepository paymentRepository;
    private final SplitPayRepository splitPayRepository;
    private final TransactionRepository transactionRepository;
    private final TransferRepository transferRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String[] DAY_OF_WEEK_KO = {"", "월", "화", "수", "목", "금", "토", "일"};

    public ExpenseResponse getExpense(String yearMonthStr) {
        YearMonth yearMonth = parseYearMonth(yearMonthStr);

        if (yearMonth.isAfter(YearMonth.now())) {
            throw new BusinessException(ExpenseErrorCode.EXPENSE_FUTURE_YEAR_MONTH);
        }

        LocalDate today = LocalDate.now();
        LocalDate lastDay = yearMonth.equals(YearMonth.now()) ? today : yearMonth.atEndOfMonth();
        LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = lastDay.plusDays(1).atStartOfDay();

        // 현재 월 트랜잭션 조회
        List<Transaction> transactions = transactionRepository
                .findAllByPeriod(startDate, endDate);

        // Payment, Transfer 조회
        Map<Long, Payment> paymentMap = paymentRepository.findByTransactionIn(transactions).stream()
                .collect(Collectors.toMap(p -> p.getTransaction().getId(), p -> p));
        Map<Long, Transfer> transferMap = transferRepository.findByTransactionIn(transactions).stream()
                .collect(Collectors.toMap(t -> t.getTransaction().getId(), t -> t));

        // 요약 계산
        long totalExpense = transactions.stream()
                .filter(t -> isExpenseType(t.getTransactionType()) && t.isIncludeInTotal())
                .mapToLong(Transaction::getAmount)
                .sum();

        long totalIncome = transactions.stream()
                .filter(t -> t.getTransactionType() == TransactionType.TRANSFER_RECEIVE)
                .mapToLong(Transaction::getAmount)
                .sum();

        long fixedExpense = transactions.stream()
                .filter(t -> isExpenseType(t.getTransactionType()) && t.isFixedExpense())
                .mapToLong(Transaction::getAmount)
                .sum();

        // 전월 총 지출 조회
        YearMonth prevMonth = yearMonth.minusMonths(1);
        LocalDateTime prevStart = prevMonth.atDay(1).atStartOfDay();
        LocalDateTime prevEnd = prevMonth.atEndOfMonth().plusDays(1).atStartOfDay();
        List<Transaction> prevTransactions = transactionRepository.findAllByPeriod(prevStart, prevEnd);

        long previousMonthTotal = prevTransactions.stream()
                .filter(t1 -> isExpenseType(t1.getTransactionType()) && t1.isIncludeInTotal())
                .mapToLong(Transaction::getAmount)
                .sum();

        // 날짜별 그룹핑
        List<DailyTransactionResponse> dailyTransactions = transactions.stream()
                .collect(Collectors.groupingBy(t -> t.getTransactedAt().toLocalDate()))
                .entrySet().stream()
                .sorted(Map.Entry.<LocalDate, List<Transaction>>comparingByKey().reversed())
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    List<Transaction> daily = entry.getValue();

                    long dailyTotal = daily.stream()
                            .mapToLong(t -> toSignedAmount(t))
                            .sum();

                    List<TransactionDetailResponse> transactionDetails = daily.stream()
                            .map(t -> new TransactionDetailResponse(
                                    t.getId(),
                                    t.getTransactionType(),
                                    t.getTransactionMethod(),
                                    resolveTransactionName(t, paymentMap, transferMap),
                                    toSignedAmount(t),
                                    t.getTransactionType() == TransactionType.TRANSFER_RECEIVE ? null : t.isIncludeInTotal()
                            ))
                            .toList();

                    return new DailyTransactionResponse(
                            date.format(DATE_FORMATTER),
                            DAY_OF_WEEK_KO[date.getDayOfWeek().getValue()],
                            dailyTotal,
                            transactionDetails
                    );
                })
                .toList();

        return new ExpenseResponse(
                yearMonth.getYear(),
                yearMonth.getMonthValue(),
                totalExpense,
                totalIncome,
                fixedExpense,
                previousMonthTotal,
                dailyTransactions
        );
    }

    private boolean isExpenseType(TransactionType type) {
        return type == TransactionType.PAYMENT || type == TransactionType.TRANSFER_SEND;
    }

    private long toSignedAmount(Transaction transaction) {
        return isExpenseType(transaction.getTransactionType())
                ? -transaction.getAmount()
                : transaction.getAmount();
    }

    private String resolveTransactionName(Transaction transaction,
                                          Map<Long, Payment> paymentMap,
                                          Map<Long, Transfer> transferMap) {
        return switch (transaction.getTransactionType()) {
            case PAYMENT -> {
                Payment payment = paymentMap.get(transaction.getId());
                yield payment.getBrandName() + "·" + payment.getOrderDescription();
            }
            case TRANSFER_SEND, TRANSFER_RECEIVE -> {
                Transfer transfer = transferMap.get(transaction.getId());
                yield transfer.getCounterpartName() + "(" + transfer.getCounterpartAccount() + ")";
            }
        };
    }

    public ExpenseAnalysisResponse getExpenseAnalysis(String yearMonthStr) {
        YearMonth yearMonth = parseYearMonth(yearMonthStr);
        LocalDate today = LocalDate.now();
        YearMonth currentYearMonth = YearMonth.from(today);

        if (yearMonth.isAfter(currentYearMonth)) {
            throw new BusinessException(ExpenseErrorCode.EXPENSE_FUTURE_YEAR_MONTH);
        }

        // 당월 날짜 범위
        LocalDate currentEndDate = yearMonth.equals(currentYearMonth)
                ? today
                : yearMonth.atEndOfMonth();
        LocalDateTime currentStart = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime currentEnd = currentEndDate.plusDays(1).atStartOfDay();


        // 전월 동기간 날짜 범위
        YearMonth prevMonth = yearMonth.minusMonths(1);
        int sameDay = Math.min(currentEndDate.getDayOfMonth(), prevMonth.lengthOfMonth());

        LocalDateTime prevStart = prevMonth.atDay(1).atStartOfDay();
        LocalDateTime prevEnd = prevMonth.atDay(sameDay).plusDays(1).atStartOfDay();

        Map<PaymentCategory, Long> currentAmounts = toCategoryAmountMap(paymentRepository.findCategoryAmountsByPeriod(currentStart, currentEnd));
        Map<PaymentCategory, Long> prevAmounts = toCategoryAmountMap(paymentRepository.findCategoryAmountsByPeriod(prevStart, prevEnd));

        if (currentAmounts.isEmpty() && prevAmounts.isEmpty()) {
            throw new BusinessException(ExpenseErrorCode.EXPENSE_ANALYSIS_NOT_FOUND);
        }

        // 총액 합산
        long monthlyCumulativeTotal = currentAmounts.values().stream().mapToLong(Long::longValue).sum();
        long previousMonthSamePeriodTotal = prevAmounts.values().stream().mapToLong(Long::longValue).sum();


        // 카테고리 리스트 조립
        Set<PaymentCategory> allCategories = EnumSet.noneOf(PaymentCategory.class);
        allCategories.addAll(currentAmounts.keySet());
        allCategories.addAll(prevAmounts.keySet());

        List<ExpenseCategoryResponse> categoryExpenses = allCategories.stream()
                .map(category -> ExpenseCategoryResponse.builder()
                        .category(category)
                        .currentMonthAmount(currentAmounts.getOrDefault(category, 0L))
                        .previousMonthAmount(prevAmounts.getOrDefault(category, 0L))
                        .build())
                .toList();

        return ExpenseAnalysisResponse.builder()
                .monthlyCumulativeTotal(monthlyCumulativeTotal)
                .previousMonthSamePeriodTotal(previousMonthSamePeriodTotal)
                .categoryExpenses(categoryExpenses)
                .build();
    }

    private YearMonth parseYearMonth(String yearMonthStr) {
        try {
            return YearMonth.parse(yearMonthStr);
        } catch (DateTimeParseException e) {
            throw new BusinessException(ExpenseErrorCode.EXPENSE_INVALID_YEAR_MONTH_FORMAT);
        }
    }

    private Map<PaymentCategory, Long> toCategoryAmountMap(List<ExpenseCategoryAmountDto> dtos) {
        return dtos.stream()
                .collect(Collectors.toMap(
                        ExpenseCategoryAmountDto::paymentCategory,
                        ExpenseCategoryAmountDto::amount
                ));
    }


    public Long getMonthlyTotalExpense() {
        YearMonth currentMonth = YearMonth.now();
        LocalDateTime startDate = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = currentMonth.atEndOfMonth().plusDays(1).atStartOfDay();
        return transactionRepository.sumMonthlyExpense(startDate, endDate);
    }

    public ExpenseDetailResponse getExpenseDetail(Long transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new BusinessException(ExpenseErrorCode.EXPENSE_DETAIL_NOT_FOUND));

        Transaction transaction = payment.getTransaction();

        Optional<SplitPay> splitPay = splitPayRepository.findByPayment(payment);

        Long totalAmount = splitPay
                .map(SplitPay::getTotalAmount)
                .orElse(transaction.getAmount());

        int participantCount = splitPay
                .map(SplitPay::getParticipantsCount)
                .orElse(1);

        return ExpenseDetailResponse.of(transaction, payment, totalAmount, participantCount);
    }
}

