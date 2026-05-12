package org.sopt.kakaopay.domain.expense.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.kakaopay.domain.expense.service.ExpenseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
}
