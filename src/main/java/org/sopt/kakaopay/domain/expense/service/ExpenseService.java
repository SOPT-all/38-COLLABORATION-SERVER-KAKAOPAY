package org.sopt.kakaopay.domain.expense.service;

import lombok.RequiredArgsConstructor;
import org.sopt.kakaopay.domain.expense.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpenseService {

    private final TransactionRepository transactionRepository;

    public Long getMonthlyTotalExpense() {
        LocalDate now = LocalDate.now();
        return transactionRepository.sumMonthlyExpense(now.getYear(), now.getMonthValue());
    }
}
