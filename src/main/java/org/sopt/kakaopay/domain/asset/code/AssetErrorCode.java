package org.sopt.kakaopay.domain.asset.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.kakaopay.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AssetErrorCode implements ErrorCode {

    ASSET_NOT_FOUND(HttpStatus.NOT_FOUND, "AST_404", "자산 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
