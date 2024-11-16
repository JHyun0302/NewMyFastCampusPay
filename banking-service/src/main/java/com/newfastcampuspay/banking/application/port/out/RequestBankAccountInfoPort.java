package com.newfastcampuspay.banking.application.port.out;

import com.newfastcampuspay.banking.adapter.out.external.bank.BankAccount;
import com.newfastcampuspay.banking.adapter.out.external.bank.GetBankAccountRequest;

public interface RequestBankAccountInfoPort {
    BankAccount getBankAccountInfo(GetBankAccountRequest request);
}
