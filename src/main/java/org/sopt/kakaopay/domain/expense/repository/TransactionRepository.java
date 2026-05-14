package org.sopt.kakaopay.domain.expense.repository;

import org.sopt.kakaopay.domain.expense.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
                SELECT t FROM Transaction t
                WHERE t.transactedAt >= :startDate
                  AND t.transactedAt < :endDate
                ORDER BY t.transactedAt DESC
            """)
    List<Transaction> findAllByPeriod(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("""
                SELECT COALESCE(SUM(t.amount), 0)
                FROM Transaction t
                WHERE t.includeInTotal = true
                  AND t.transactedAt >= :startDate
                  AND t.transactedAt < :endDate
            """)
    Long sumMonthlyExpense(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
