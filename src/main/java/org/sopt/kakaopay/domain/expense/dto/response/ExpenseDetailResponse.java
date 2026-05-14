package org.sopt.kakaopay.domain.expense.dto.response;

import org.sopt.kakaopay.domain.expense.entity.Payment;
import org.sopt.kakaopay.domain.expense.entity.Transaction;
import org.sopt.kakaopay.domain.expense.enums.PaymentCategory;
import org.sopt.kakaopay.domain.expense.enums.TransactionMethod;

import java.time.LocalDateTime;

public record ExpenseDetailResponse(
        Long id,
        String expenseName,
        TransactionMethod paymentMethod,
        Long splitAmount,
        Long totalAmount,
        int participantCount,
        String orderNumber,
        LocalDateTime orderedAt,
        PaymentCategory category,
        boolean includeInTotal
) {
    public static ExpenseDetailResponse of(
            Transaction transaction, Payment payment,
            Long totalAmount, int participantCount
    ) {
        return new ExpenseDetailResponse(
                transaction.getId(),
                payment.getOrderDescription(),
                transaction.getTransactionMethod(),
                transaction.getAmount(),
                totalAmount,
                participantCount,
                payment.getOrderNumber(),
                transaction.getTransactedAt(),
                payment.getPaymentCategory(),
                transaction.isIncludeInTotal()
        );
    }
}
