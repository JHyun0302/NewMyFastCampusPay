package com.newfastcampuspay.payment.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentJpaEntity {

    @Id
    @GeneratedValue
    private Long paymentId;

    private String requestMembershipId;

    private int requestPrice;

    private String franchiseId;

    private String franchiseFeeRate;

    private int paymentStatus;  // 0: 승인, 1: 실패 , 2: 정산 완료

    private Date approvedAt;

    public PaymentJpaEntity(String requestMembershipId, int requestPrice, String franchiseId,
                            String franchiseFeeRate, int paymentStatus, Date approvedAt) {
        this.requestMembershipId = requestMembershipId;
        this.requestPrice = requestPrice;
        this.franchiseId = franchiseId;
        this.franchiseFeeRate = franchiseFeeRate;
        this.paymentStatus = paymentStatus;
        this.approvedAt = approvedAt;
    }
}
