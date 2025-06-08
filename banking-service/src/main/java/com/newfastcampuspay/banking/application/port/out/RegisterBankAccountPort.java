package com.newfastcampuspay.banking.application.port.out;

import com.newfastcampuspay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount;

public interface RegisterBankAccountPort {
    RegisteredBankAccountJpaEntity createRegisteredBankAccount(
            RegisteredBankAccount.MembershipId membershipId,
            RegisteredBankAccount.BankName bankName,
            RegisteredBankAccount.BankAccountNumber bankAccountNumber,
            RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid,
            RegisteredBankAccount.AggregateIdentifier aggregateIdentifier
            );
}
