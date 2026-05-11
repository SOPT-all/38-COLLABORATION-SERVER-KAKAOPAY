package org.sopt.kakaopay.domain.expense.entity;

import org.sopt.kakaopay.domain.expense.enums.PaymentCategory;

public class PaymentDetail {
    private Long id;
    private Long transactionId;
    private PaymentCategory paymentCategory;
    private String brandName;
    private String brandLogoUrl;
    private String orderDescription;
    private String orderNumber;
}
