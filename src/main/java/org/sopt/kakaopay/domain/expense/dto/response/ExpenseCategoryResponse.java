package org.sopt.kakaopay.domain.expense.dto.response;

import lombok.Builder;
import org.sopt.kakaopay.domain.expense.enums.PaymentCategory;

@Builder
public record ExpenseCategoryResponse(
        PaymentCategory category,
        Long currentMonthAmount,
        Long previousMonthAmount
) {
}
