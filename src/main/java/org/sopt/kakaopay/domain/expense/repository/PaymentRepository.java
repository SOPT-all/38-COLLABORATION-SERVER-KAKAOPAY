package org.sopt.kakaopay.domain.expense.repository;

import org.sopt.kakaopay.domain.expense.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
