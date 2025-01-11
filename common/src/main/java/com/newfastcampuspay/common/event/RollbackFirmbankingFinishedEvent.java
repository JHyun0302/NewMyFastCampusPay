package com.newfastcampuspay.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RollbackFirmbankingFinishedEvent {

    private String rollbackFirmbankingId;

    private String membershipId;

    private String rollbackFirmbankingAggregateIdentifier;

}
