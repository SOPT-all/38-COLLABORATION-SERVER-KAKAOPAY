package org.sopt.kakaopay.domain.expense.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.kakaopay.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExpenseErrorCode implements ErrorCode {

    EXPENSE_NOT_FOUND(HttpStatus.NOT_FOUND, "EXP_404", "지출 내역을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
