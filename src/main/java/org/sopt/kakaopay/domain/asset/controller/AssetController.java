package org.sopt.kakaopay.domain.asset.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.kakaopay.domain.asset.annotations.ApiAssetResponse;
import org.sopt.kakaopay.domain.asset.code.AssetSuccessCode;
import org.sopt.kakaopay.domain.asset.dto.response.AssetResponse;
import org.sopt.kakaopay.domain.asset.service.AssetService;
import org.sopt.kakaopay.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Asset", description = "자산 관련 API")
@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @ApiAssetResponse
    @GetMapping
    public ResponseEntity<BaseResponse<AssetResponse>> getAsset() {
        return ResponseEntity.ok(BaseResponse.success(AssetSuccessCode.GET_ASSET, assetService.getAsset()));
    }
}
