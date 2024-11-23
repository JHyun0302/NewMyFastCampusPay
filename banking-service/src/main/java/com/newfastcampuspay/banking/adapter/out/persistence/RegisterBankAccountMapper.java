package com.newfastcampuspay.banking.adapter.out.persistence;

import com.newfastcampuspay.banking.domain.RegisteredBankAccount;
import org.springframework.stereotype.Component;

@Component
public class RegisterBankAccountMapper {
    public RegisteredBankAccount mapToDomainEntity(RegisteredBankAccountJpaEntity registeredBankAccountJpaEntity) {
        return RegisteredBankAccount.generateRegisteredBankAccount(
                new RegisteredBankAccount.RegisteredBankAccountId(registeredBankAccountJpaEntity.getRegisteredBankAccountId() + ""),
                new RegisteredBankAccount.MembershipId(registeredBankAccountJpaEntity.getMembershipId() + ""),
                new RegisteredBankAccount.BankName(registeredBankAccountJpaEntity.getBankName()),
                new RegisteredBankAccount.BankAccountNumber(registeredBankAccountJpaEntity.getBankAccountNumber()),
                new RegisteredBankAccount.LinkedStatusIsValid(registeredBankAccountJpaEntity.isLinkedStatusIsValid())
        );
    }
}
