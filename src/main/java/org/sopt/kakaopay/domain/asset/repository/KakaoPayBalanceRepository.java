package org.sopt.kakaopay.domain.asset.repository;

import org.sopt.kakaopay.domain.asset.entity.KakaoPayBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoPayBalanceRepository extends JpaRepository<KakaoPayBalance, Long> {
}
