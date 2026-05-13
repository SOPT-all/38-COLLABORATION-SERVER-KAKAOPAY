package org.sopt.kakaopay.domain.expense.dto.response;

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
}
