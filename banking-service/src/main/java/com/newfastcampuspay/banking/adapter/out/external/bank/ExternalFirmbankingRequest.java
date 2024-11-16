package com.newfastcampuspay.banking.adapter.out.external.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalFirmbankingRequest {
    private String fomBankName;

    private String fromBankAccountNumber;

    private String toBankName;

    private String toBankAccountNumber;
}

