package com.newfastcampuspay.banking.adapter.out.persistence;

import com.newfastcampuspay.banking.application.port.in.GetRegisteredBankAccountCommand;
import com.newfastcampuspay.banking.application.port.out.GetRegisteredBankAccountPort;
import com.newfastcampuspay.banking.application.port.out.RegisterBankAccountPort;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.AggregateIdentifier;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.BankAccountNumber;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.BankName;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.LinkedStatusIsValid;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.MembershipId;
import com.newfastcampuspay.common.PersistenceAdapter;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisterBankAccountPersistenceAdapter implements RegisterBankAccountPort, GetRegisteredBankAccountPort {

    private final SpringDataRegisteredBankAccountRepository bankAccountRepository;

    @Override
    public RegisteredBankAccountJpaEntity createRegisteredBankAccount(MembershipId membershipId, BankName bankName, BankAccountNumber bankAccountNumber, LinkedStatusIsValid linkedStatusIsValid, AggregateIdentifier aggregateIdentifier) {
        return bankAccountRepository.save(
                new RegisteredBankAccountJpaEntity(
                        membershipId.getMembershipId(),
                        bankName.getBankName(),
                        bankAccountNumber.getBankAccountNumber(),
                        linkedStatusIsValid.isLinkedStatusIsValid(),
                        aggregateIdentifier.getAggregateIdentifier()
                )
        );
    }

    @Override
    public RegisteredBankAccountJpaEntity getRegisteredBankAccount(GetRegisteredBankAccountCommand command) {
        List<RegisteredBankAccountJpaEntity> entityList = bankAccountRepository.findByMembershipId(command.getMembershipId());
        if (entityList.size() > 0) {
            return entityList.get(0);
        }
        return null;
    }
}
