package org.sopt.kakaopay.domain.expense.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "split_pay")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SplitPay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(nullable = false)
    private Long totalAmount;

    @Column(nullable = false)
    private Long myAmount;

    @Column(nullable = false)
    private int participantsCount;

    @Builder
    private SplitPay(Payment payment, Long totalAmount, Long myAmount, int participantsCount) {
        this.payment = payment;
        this.totalAmount = totalAmount;
        this.myAmount = myAmount;
        this.participantsCount = participantsCount;
    }
}
