package org.sopt.kakaopay.domain.asset.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.kakaopay.domain.asset.service.AssetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/asset")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;
}
