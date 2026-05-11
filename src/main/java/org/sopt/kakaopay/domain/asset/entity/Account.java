package org.sopt.kakaopay.domain.asset.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String bank;

    @Column(nullable = false, length = 4)
    private String accountNumber;

    @Builder
    private Account(String bank, String accountNumber) {
        this.bank = bank;
        this.accountNumber = accountNumber;
    }
}
