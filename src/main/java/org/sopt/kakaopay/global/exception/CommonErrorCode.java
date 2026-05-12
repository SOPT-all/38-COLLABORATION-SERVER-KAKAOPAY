package org.sopt.kakaopay.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    // 400 Bad Request
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "CMN_400", "잘못된 요청입니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "CMN_400_1", "유효하지 않은 파라미터입니다."),
    MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "CMN_400_2", "필수 파라미터가 누락되었습니다."),
    INVALID_TYPE(HttpStatus.BAD_REQUEST, "CMN_400_3", "요청 값의 타입이 올바르지 않습니다."),

    // 404 Not Found
    NOT_FOUND(HttpStatus.NOT_FOUND, "CMN_404", "요청한 리소스를 찾을 수 없습니다."),

    // 405 Method Not Allowed
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "CMN_405", "허용되지 않은 HTTP 메서드입니다."),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CMN_500", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
