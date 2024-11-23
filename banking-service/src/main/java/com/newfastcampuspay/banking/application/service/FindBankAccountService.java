package com.newfastcampuspay.banking.application.service;

import com.newfastcampuspay.banking.adapter.out.persistence.RegisterBankAccountMapper;
import com.newfastcampuspay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.newfastcampuspay.banking.application.port.in.FindBankAccountCommand;
import com.newfastcampuspay.banking.application.port.in.FindBankAccountUseCase;
import com.newfastcampuspay.banking.application.port.out.FindBankAccountPort;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.MembershipId;
import com.newfastcampuspay.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class FindBankAccountService implements FindBankAccountUseCase {

    private final FindBankAccountPort findBankAccountPort;

    private final RegisterBankAccountMapper mapper;

    @Override
    public RegisteredBankAccount findBankAccount(FindBankAccountCommand command) {
        RegisteredBankAccountJpaEntity jpaEntity = findBankAccountPort.findBankAccount(
                new RegisteredBankAccount.RegisteredBankAccountId(command.getMembershipId()));

        return mapper.mapToDomainEntity(jpaEntity);
    }
}
