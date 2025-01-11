package com.newfastcampuspay.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestFirmbankingFinishedEvent {

    private String requestFirmbankingId;

    private String rechargingRequestId;

    private String membershipId;

    private String toBankName;

    private String toBankAccountNumber;

    private int moneyAmount; // only won

    private int status;

    private String requestFirmbankingAggregateIdentifier;
}
