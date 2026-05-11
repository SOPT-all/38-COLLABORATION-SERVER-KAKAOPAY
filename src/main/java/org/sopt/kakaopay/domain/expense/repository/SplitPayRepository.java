package org.sopt.kakaopay.domain.expense.repository;

import org.sopt.kakaopay.domain.expense.entity.SplitPay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SplitPayRepository extends JpaRepository<SplitPay, Long> {
}
