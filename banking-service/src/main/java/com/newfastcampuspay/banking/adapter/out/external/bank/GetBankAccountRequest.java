package com.newfastcampuspay.banking.adapter.out.external.bank;

import lombok.Data;

@Data
public class GetBankAccountRequest {
    private String bankName;
    private String bankAccountNumber;

    public GetBankAccountRequest(String bankAccountNumber, String bankName) {
        this.bankAccountNumber = bankAccountNumber;
        this.bankName = bankName;
    }
}
