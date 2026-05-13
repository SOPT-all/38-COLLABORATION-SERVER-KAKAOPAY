package org.sopt.kakaopay.domain.expense.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.kakaopay.domain.expense.code.ExpenseSuccessCode;
import org.sopt.kakaopay.domain.expense.dto.response.ExpenseAnalysisResponse;
import org.sopt.kakaopay.domain.expense.service.ExpenseService;
import org.sopt.kakaopay.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Expense", description = "지출 관련 API")
@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @Operation(summary = "상세 소비 분석 조회", description = "연월 기준 카테고리별 당월/전월 누적 지출을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상세 소비 분석 조회 성공"),
            @ApiResponse(responseCode = "400", description = "yearMonth 형식이 올바르지 않습니다."),
            @ApiResponse(responseCode = "404", description = "해당 연월의 소비 분석을 찾을 수 없습니다.")
    })
    @GetMapping("/analysis")
    public ResponseEntity<BaseResponse<ExpenseAnalysisResponse>> getExpenseAnalysis(
            @Parameter(description = "조회할 연월 (yyyy-MM 형식)", example = "2026-05")
            @RequestParam String yearMonth
    ) {
        return ResponseEntity.ok(
                BaseResponse.success(
                        ExpenseSuccessCode.GET_EXPENSE_ANALYSIS,
                        expenseService.getExpenseAnalysis(yearMonth)
                )
        );
    }
}
