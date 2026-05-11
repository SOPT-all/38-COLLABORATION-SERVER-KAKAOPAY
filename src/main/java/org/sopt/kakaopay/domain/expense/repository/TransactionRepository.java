package org.sopt.kakaopay.domain.expense.repository;

import org.sopt.kakaopay.domain.expense.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
