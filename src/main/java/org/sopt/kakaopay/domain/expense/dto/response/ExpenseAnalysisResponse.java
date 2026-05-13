package org.sopt.kakaopay.domain.expense.dto.response;

import lombok.Builder;
import java.util.List;

@Builder
public record ExpenseAnalysisResponse(
        Long monthlyCumulativeTotal,
        Long previousMonthSamePeriodTotal,
        List<ExpenseCategoryResponse> categoryExpenses
) {
}
