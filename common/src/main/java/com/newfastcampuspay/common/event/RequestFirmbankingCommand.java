package com.newfastcampuspay.common.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestFirmbankingCommand {

    private String requestFirmbankingId;

    @TargetAggregateIdentifier
    private String aggregateIdentifier;

    private String rechargingRequestId;

    private String membershipId;

    private String fromBankName;

    private String fromBankAccountNumber;

    private String toBankName;

    private String toBankAccountNumber;

    private int moneyAmount; //only won

}
