package org.sopt.kakaopay.domain.expense.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.kakaopay.domain.expense.code.ExpenseSuccessCode;
import org.sopt.kakaopay.domain.expense.dto.response.ExpenseAnalysisResponse;
import org.sopt.kakaopay.domain.expense.dto.response.ExpenseDetailResponse;
import org.sopt.kakaopay.domain.expense.service.ExpenseService;
import org.sopt.kakaopay.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Expense", description = "지출 관련 API")
@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @Operation(summary = "상세 소비 분석 조회", description = "연월 기준 카테고리별 당월/전월 누적 지출을 조회합니다.")
    @ExpenseApiResponses.GetExpenseAnalysis
    @GetMapping("/analysis")
    public ResponseEntity<BaseResponse<ExpenseAnalysisResponse>> getExpenseAnalysis(
            @Parameter(description = "조회할 연월 (yyyy-MM 형식)", example = "2026-04")
            @RequestParam String yearMonth
    ) {
        return ResponseEntity.ok(
                BaseResponse.success(
                        ExpenseSuccessCode.GET_EXPENSE_ANALYSIS,
                        expenseService.getExpenseAnalysis(yearMonth)
                )
        );
    }

    @Operation(summary = "지출 상세 내역 조회", description = "거래 ID로 지출 상세 내역을 조회합니다.")
    @ExpenseApiResponses.GetExpenseDetail
    @GetMapping("/{transactionId}")
    public ResponseEntity<BaseResponse<ExpenseDetailResponse>> getExpenseDetail(
            @Parameter(description = "거래 ID", example = "1")
            @PathVariable Long transactionId
    ) {
        return ResponseEntity.ok(
                BaseResponse.success(
                        ExpenseSuccessCode.GET_EXPENSE_DETAIL,
                        expenseService.getExpenseDetail(transactionId)
                )
        );
    }
}
