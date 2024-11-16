package com.newfastcampuspay.banking.adapter.out.persistence;

import com.newfastcampuspay.banking.application.port.out.RegisterBankAccountPort;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.BankAccountNumber;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.BankName;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.LinkedStatusIsValid;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.MembershipId;
import com.newfastcampuspay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisterBankAccountPersistenceAdapter implements RegisterBankAccountPort {

    private final SpringDataRegisteredBankAccountRepository bankAccountRepository;

    @Override
    public RegisteredBankAccountJpaEntity createRegisteredBankAccount(MembershipId membershipId, BankName bankName, BankAccountNumber bankAccountNumber, LinkedStatusIsValid linkedStatusIsValid) {
        return bankAccountRepository.save(
                new RegisteredBankAccountJpaEntity(
                        membershipId.getMebershipId(),
                        bankName.getBankName(),
                        bankAccountNumber.getBankAccountNumber(),
                        linkedStatusIsValid.isLinkedStatusIsValid()
                )
        );
    }
}
