package org.sopt.kakaopay.domain.expense.dto.response;

import java.util.List;

public record ExpenseResponse(
        int year,
        int month,
        Long totalExpense,
        Long totalIncome,
        Long fixedExpense,
        Long previousMonthTotal,
        List<DailyTransactionResponse> dailyTransactions
) {
}
