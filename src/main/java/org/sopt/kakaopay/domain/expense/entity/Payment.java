package org.sopt.kakaopay.domain.expense.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.kakaopay.domain.expense.enums.PaymentCategory;

@Getter
@Entity
@Table(name = "payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentCategory paymentCategory;

    @Column(length = 20)
    private String brandName;

    @Column(nullable = false)
    private String orderDescription;

    @Column(length = 50)
    private String orderNumber;

    @Builder
    private Payment(Transaction transaction, PaymentCategory paymentCategory, String brandName,
                    String orderDescription, String orderNumber) {
        this.transaction = transaction;
        this.paymentCategory = paymentCategory;
        this.brandName = brandName;
        this.orderDescription = orderDescription;
        this.orderNumber = orderNumber;
    }
}
