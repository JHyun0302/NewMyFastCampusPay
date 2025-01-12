package com.newfastcampuspay.payment.adapter.in.web;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private String requestMembershipId;

    private String requestPrice;

    private String franchiseId;

    private String franchiseFeeRate;

    private int paymentStatus;

    private Date approvedAt;

}
