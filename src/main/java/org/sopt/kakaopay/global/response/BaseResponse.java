package org.sopt.kakaopay.global.response;

public record BaseResponse<T>(
        String code,
        String message,
        T data
) {
    public static <T> BaseResponse<T> success(SuccessCode successCode, T data) {
        return new BaseResponse<>(successCode.getCode(), successCode.getMessage(), data);
    }

    public static BaseResponse<Void> success(SuccessCode successCode) {
        return new BaseResponse<>(successCode.getCode(), successCode.getMessage(), null);
    }
}
