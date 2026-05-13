package org.sopt.kakaopay.domain.asset.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.kakaopay.domain.asset.code.AssetSuccessCode;
import org.sopt.kakaopay.domain.asset.dto.response.AssetResponse;
import org.sopt.kakaopay.domain.asset.service.AssetService;
import org.sopt.kakaopay.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @GetMapping
    public ResponseEntity<BaseResponse<AssetResponse>> getAsset() {
        return ResponseEntity.ok(BaseResponse.success(AssetSuccessCode.GET_ASSET, assetService.getAsset()));
    }
}
