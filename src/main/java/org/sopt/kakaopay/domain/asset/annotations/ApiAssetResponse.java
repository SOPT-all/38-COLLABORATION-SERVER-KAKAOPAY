package org.sopt.kakaopay.domain.asset.annotations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.sopt.kakaopay.domain.asset.dto.response.AssetResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "자산 조회", description = "카카오페이 머니 잔액, 자주 쓰는 계좌, 이번 달 총 지출을 조회합니다.")
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "자산 조회 성공",
                content = @Content(
                        schema = @Schema(implementation = AssetResponse.class),
                        examples = @ExampleObject(
                                name = "자산 조회 성공 예시",
                                value = """
                                        {
                                          "code": "AST_200",
                                          "message": "자산 조회에 성공했습니다.",
                                          "data": {
                                            "kakaopayBalance": 12300,
                                            "favoriteAccounts": [
                                              {
                                                "accountId": 1,
                                                "accountInfo": "카카오뱅크 1785"
                                              },
                                              {
                                                "accountId": 2,
                                                "accountInfo": "토스뱅크 0000"
                                              },
                                              {
                                                "accountId": 3,
                                                "accountInfo": "대구은행 0000"
                                              }
                                            ],
                                            "totalExpense": 257755
                                          }
                                        }
                                        """
                        )
                )
        )
})
public @interface ApiAssetResponse {
}
