package org.sopt.kakaopay.domain.expense.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.kakaopay.domain.expense.enums.TransactionMethod;
import org.sopt.kakaopay.domain.expense.enums.TransactionType;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "pay_transaction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType transactionType;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private LocalDateTime transactedAt;

    @Column(nullable = false)
    private Boolean isFixedExpense;

    @Column(nullable = false)
    private Boolean includeInTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionMethod transactionMethod;

    @Builder
    private Transaction(TransactionType transactionType, Long amount, LocalDateTime transactedAt,
                        Boolean isFixedExpense, Boolean includeInTotal, TransactionMethod transactionMethod) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactedAt = transactedAt;
        this.isFixedExpense = isFixedExpense;
        this.includeInTotal = includeInTotal;
        this.transactionMethod = transactionMethod;
    }
}
