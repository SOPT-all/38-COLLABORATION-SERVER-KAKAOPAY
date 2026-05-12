package org.sopt.kakaopay.domain.asset.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.kakaopay.global.response.SuccessCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AssetSuccessCode implements SuccessCode {

    GET_ASSET(HttpStatus.OK, "AST_200", "자산 조회에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
