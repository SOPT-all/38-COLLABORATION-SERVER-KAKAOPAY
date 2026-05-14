package org.sopt.kakaopay.domain.expense.dto.response;

import java.util.List;

public record DailyTransactionResponse(
        String date,
        String dayOfWeek,
        Long dailyTotal,
        List<TransactionDetailResponse> transactions
) {
}
