package org.sopt.kakaopay.domain.asset.repository;

import org.sopt.kakaopay.domain.asset.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
