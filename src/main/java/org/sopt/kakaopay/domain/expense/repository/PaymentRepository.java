package org.sopt.kakaopay.domain.expense.repository;

import org.sopt.kakaopay.domain.expense.dto.ExpenseCategoryAmountDto;
import org.sopt.kakaopay.domain.expense.entity.Payment;
import org.sopt.kakaopay.domain.expense.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByTransactionId(Long transactionId);

    List<Payment> findByTransactionIn(List<Transaction> transactions);

    @Query("""                                                                                                                                                                                                                       
            SELECT new org.sopt.kakaopay.domain.expense.dto.ExpenseCategoryAmountDto(
            p.paymentCategory, SUM(t.amount)
            )
            FROM Payment p JOIN p.transaction t
            WHERE t.transactedAt >= :start
              AND t.transactedAt < :end
              AND t.includeInTotal = true
            GROUP BY p.paymentCategory
            """)
    List<ExpenseCategoryAmountDto> findCategoryAmountsByPeriod(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
