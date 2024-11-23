package com.newfastcampuspay.banking.application.port.out;

import com.newfastcampuspay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount;

public interface FindBankAccountPort {

    RegisteredBankAccountJpaEntity findBankAccount(
        RegisteredBankAccount.RegisteredBankAccountId registeredBankAccountId
    );
}
