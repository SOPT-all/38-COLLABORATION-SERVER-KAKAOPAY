package org.sopt.kakaopay.domain.asset.dto.response;

import java.util.List;

public record AssetResponse(
        Long kakaopayBalance,
        List<AccountInfo> favoriteAccounts,
        Long totalExpense
) {
    public record AccountInfo(
            Long accountId,
            String accountInfo
    ) {
    }
}
