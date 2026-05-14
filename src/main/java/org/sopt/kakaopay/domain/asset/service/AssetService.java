package org.sopt.kakaopay.domain.asset.service;

import lombok.RequiredArgsConstructor;
import org.sopt.kakaopay.domain.asset.dto.response.AssetResponse;
import org.sopt.kakaopay.domain.asset.entity.KakaoPayBalance;
import org.sopt.kakaopay.domain.asset.repository.AccountRepository;
import org.sopt.kakaopay.domain.asset.repository.KakaoPayBalanceRepository;
import org.sopt.kakaopay.domain.expense.service.ExpenseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssetService {

    private final KakaoPayBalanceRepository kakaoPayBalanceRepository;
    private final AccountRepository accountRepository;
    private final ExpenseService expenseService;

    public AssetResponse getAsset() {
        Long kakaopayBalance = kakaoPayBalanceRepository.findAll().stream()
                .findFirst()
                .map(KakaoPayBalance::getBalance)
                .orElse(0L);

        List<AssetResponse.AccountInfo> favoriteAccounts = accountRepository.findAll().stream()
                .limit(3)
                .map(account -> new AssetResponse.AccountInfo(
                        account.getId(),
                        account.getBank() + " " + account.getAccountNumber()
                ))
                .toList();

        Long totalExpense = expenseService.getMonthlyTotalExpense();

        return new AssetResponse(kakaopayBalance, favoriteAccounts, totalExpense);
    }
}
