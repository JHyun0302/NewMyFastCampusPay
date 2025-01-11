package com.newfastcampuspay.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * RegisteredBankAccount의 aggregateIdentifier가 membershipId 고객에 대해 정상적인지 판단하는 커맨드
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckRegisteredBankAccountCommand {

    @TargetAggregateIdentifier
    private String aggregateIdentifier; //RegisteredBankAccount

    private String rechargingRequestId;

    private String checkRegisteredBankAccountId;

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;

    private int amount;

}
