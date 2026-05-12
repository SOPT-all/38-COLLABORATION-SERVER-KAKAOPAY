package org.sopt.kakaopay.domain.expense.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "transfer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Column(nullable = false, length = 10)
    private String counterpartName;

    @Column(nullable = false, length = 20)
    private String counterpartAccount;

    @Builder
    private Transfer(Transaction transaction, String counterpartName, String counterpartAccount) {
        this.transaction = transaction;
        this.counterpartName = counterpartName;
        this.counterpartAccount = counterpartAccount;
    }
}
