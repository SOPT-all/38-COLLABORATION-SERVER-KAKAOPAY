package org.sopt.kakaopay.domain.expense.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.kakaopay.global.response.SuccessCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExpenseSuccessCode implements SuccessCode {

    GET_EXPENSE(HttpStatus.OK, "EXP_200", "지출 내역 조회에 성공했습니다."),
    GET_EXPENSE_ANALYSIS(HttpStatus.OK, "EXP_ANALYSIS_200", "상세 소비 분석 조회에 성공했습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
