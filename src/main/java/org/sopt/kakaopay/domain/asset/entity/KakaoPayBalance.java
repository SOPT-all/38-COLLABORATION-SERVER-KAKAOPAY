package org.sopt.kakaopay.domain.asset.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "kakao_pay_balance")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoPayBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long balance;

    @Builder
    private KakaoPayBalance(Long balance) {
        this.balance = balance;
    }
}
