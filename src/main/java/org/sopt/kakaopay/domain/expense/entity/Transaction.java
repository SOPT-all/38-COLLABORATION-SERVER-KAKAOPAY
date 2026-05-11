package org.sopt.kakaopay.domain.expense.entity;

import org.sopt.kakaopay.domain.expense.enums.TransactionMethod;
import org.sopt.kakaopay.domain.expense.enums.TransactionType;

import java.time.LocalDateTime;

public class Transaction {
    private Long id;
    private TransactionType transactionType;
    private Long amount;
    private LocalDateTime transactedAt;
    private Boolean isFixedExpense;
    private Boolean includeInTotal;
    private TransactionMethod transactionMethod;
}
