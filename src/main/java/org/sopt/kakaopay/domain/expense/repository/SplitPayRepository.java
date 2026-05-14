package org.sopt.kakaopay.domain.expense.repository;

import org.sopt.kakaopay.domain.expense.entity.Payment;
import org.sopt.kakaopay.domain.expense.entity.SplitPay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SplitPayRepository extends JpaRepository<SplitPay, Long> {
    Optional<SplitPay> findByPayment(Payment payment);
}
