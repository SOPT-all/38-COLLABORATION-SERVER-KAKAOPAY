package org.sopt.kakaopay.domain.expense.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.kakaopay.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExpenseErrorCode implements ErrorCode {

    EXPENSE_NOT_FOUND(HttpStatus.NOT_FOUND, "EXP_404", "지출 내역을 찾을 수 없습니다."),
    EXPENSE_ANALYSIS_NOT_FOUND(HttpStatus.NOT_FOUND, "EXP_ANALYSIS_404", "해당 연월의 소비 분석을 찾을 수 없습니다."),
    EXPENSE_ANALYSIS_INVALID_YEAR_MONTH(HttpStatus.BAD_REQUEST, "EXP_ANALYSIS_400", "yearMonth 형식이 올바르지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
