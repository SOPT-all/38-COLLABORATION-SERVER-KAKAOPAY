package org.sopt.kakaopay.domain.expense.service;

import lombok.RequiredArgsConstructor;
import org.sopt.kakaopay.domain.expense.code.ExpenseErrorCode;
import org.sopt.kakaopay.domain.expense.dto.ExpenseCategoryAmountDto;
import org.sopt.kakaopay.domain.expense.dto.response.ExpenseAnalysisResponse;
import org.sopt.kakaopay.domain.expense.dto.response.ExpenseCategoryResponse;
import org.sopt.kakaopay.domain.expense.dto.response.ExpenseDetailResponse;
import org.sopt.kakaopay.domain.expense.entity.Payment;
import org.sopt.kakaopay.domain.expense.entity.SplitPay;
import org.sopt.kakaopay.domain.expense.entity.Transaction;
import org.sopt.kakaopay.domain.expense.enums.PaymentCategory;
import org.sopt.kakaopay.domain.expense.repository.PaymentRepository;
import org.sopt.kakaopay.domain.expense.repository.SplitPayRepository;
import org.sopt.kakaopay.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpenseService {
    private final PaymentRepository paymentRepository;
    private final SplitPayRepository splitPayRepository;

    public ExpenseAnalysisResponse getExpenseAnalysis(String yearMonthStr) {
        YearMonth yearMonth = parseYearMonth(yearMonthStr);
        LocalDate today = LocalDate.now();
        YearMonth currentYearMonth = YearMonth.from(today);

        if (yearMonth.isAfter(currentYearMonth)) {
            throw new BusinessException(ExpenseErrorCode.EXPENSE_ANALYSIS_INVALID_YEAR_MONTH);
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

    public ExpenseDetailResponse getExpenseDetail(Long expenseId) {
        Payment payment = paymentRepository.findByTransactionId(expenseId)
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

    private YearMonth parseYearMonth(String yearMonthStr) {
        try {
            return YearMonth.parse(yearMonthStr);
        } catch (DateTimeParseException e) {
            throw new BusinessException(ExpenseErrorCode.EXPENSE_ANALYSIS_INVALID_YEAR_MONTH);
        }
    }

    private Map<PaymentCategory, Long> toCategoryAmountMap(List<ExpenseCategoryAmountDto> dtos) {
        return dtos.stream()
                .collect(Collectors.toMap(
                        ExpenseCategoryAmountDto::paymentCategory,
                        ExpenseCategoryAmountDto::amount
                ));
    }
}

