package org.sopt.kakaopay.domain.expense.dto;

import org.sopt.kakaopay.domain.expense.enums.PaymentCategory;

public record ExpenseCategoryAmountDto(
        PaymentCategory paymentCategory,
        Long amount
) {
}
