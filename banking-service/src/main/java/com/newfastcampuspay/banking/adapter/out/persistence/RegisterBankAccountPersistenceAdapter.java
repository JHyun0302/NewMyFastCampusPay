package com.newfastcampuspay.banking.adapter.out.persistence;

import com.newfastcampuspay.banking.application.port.out.FindBankAccountPort;
import com.newfastcampuspay.banking.application.port.out.RegisterBankAccountPort;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.BankAccountNumber;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.BankName;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.LinkedStatusIsValid;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.MembershipId;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.RegisteredBankAccountId;
import com.newfastcampuspay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisterBankAccountPersistenceAdapter implements RegisterBankAccountPort, FindBankAccountPort {

    private final SpringDataRegisteredBankAccountRepository bankAccountRepository;

    @Override
    public RegisteredBankAccountJpaEntity createRegisteredBankAccount(MembershipId membershipId, BankName bankName, BankAccountNumber bankAccountNumber, LinkedStatusIsValid linkedStatusIsValid) {
        return bankAccountRepository.save(
                new RegisteredBankAccountJpaEntity(
                        membershipId.getMembershipId(),
                        bankName.getBankName(),
                        bankAccountNumber.getBankAccountNumber(),
                        linkedStatusIsValid.isLinkedStatusIsValid()
                )
        );
    }

    @Override
    public RegisteredBankAccountJpaEntity findBankAccount(RegisteredBankAccountId registeredBankAccountId) {
        return bankAccountRepository.findByRegisteredBankAccountId(Long.valueOf(registeredBankAccountId.getRegisteredBankAccountId()));
    }
}
