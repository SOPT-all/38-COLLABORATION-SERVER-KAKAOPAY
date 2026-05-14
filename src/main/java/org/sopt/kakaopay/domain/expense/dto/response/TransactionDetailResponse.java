package org.sopt.kakaopay.domain.expense.dto.response;

import org.sopt.kakaopay.domain.expense.enums.TransactionMethod;
import org.sopt.kakaopay.domain.expense.enums.TransactionType;

public record TransactionDetailResponse(
        Long transactionId,
        TransactionType transactionType,
        TransactionMethod transactionMethod,
        String transactionName,
        Long amount,
        Boolean includeInTotal
) {
}
