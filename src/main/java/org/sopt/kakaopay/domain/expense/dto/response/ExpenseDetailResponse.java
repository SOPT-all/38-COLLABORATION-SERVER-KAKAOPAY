package org.sopt.kakaopay.domain.expense.dto.response;

import lombok.Builder;
import org.sopt.kakaopay.domain.expense.enums.PaymentCategory;
import org.sopt.kakaopay.domain.expense.enums.TransactionMethod;

import java.time.LocalDateTime;

@Builder
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
