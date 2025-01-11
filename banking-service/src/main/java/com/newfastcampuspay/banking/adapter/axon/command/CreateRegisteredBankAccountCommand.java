package com.newfastcampuspay.banking.adapter.axon.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateRegisteredBankAccountCommand {

    private String membershipId;

    private String bankName;

    private String bankAccountName;

}