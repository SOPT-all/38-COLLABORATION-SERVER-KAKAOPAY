package org.sopt.kakaopay.domain.expense.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.sopt.kakaopay.global.exception.ErrorResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class ExpenseApiResponses {
    private ExpenseApiResponses() {}

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "지출 내역 조회 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = "현재 연월 이후 조회 불가 / yearMonth 형식 오류",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public @interface GetExpense {}

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상세 소비 분석 조회 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = "yearMonth 형식이 올바르지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "해당 연월의 소비 분석을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public @interface GetExpenseAnalysis {}

}
