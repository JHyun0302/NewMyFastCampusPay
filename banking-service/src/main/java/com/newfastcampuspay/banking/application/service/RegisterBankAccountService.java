package com.newfastcampuspay.banking.application.service;

import com.newfastcampuspay.banking.adapter.out.external.bank.BankAccount;
import com.newfastcampuspay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.newfastcampuspay.banking.adapter.out.persistence.RegisterBankAccountMapper;
import com.newfastcampuspay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.newfastcampuspay.banking.application.port.in.RegisterBankAccountCommand;
import com.newfastcampuspay.banking.application.port.in.RegisterBankAccountUseCase;
import com.newfastcampuspay.banking.application.port.out.RegisterBankAccountPort;
import com.newfastcampuspay.banking.application.port.out.RequestBankAccountInfoPort;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.BankAccountNumber;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.BankName;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.LinkedStatusIsValid;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.MembershipId;
import com.newfastcampuspay.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterBankAccountService implements RegisterBankAccountUseCase {

    private final RegisterBankAccountPort registerBankAccountPort;

    private final RegisterBankAccountMapper mapper;

    private final RequestBankAccountInfoPort requestBankAccountInfoPort;

    @Override
    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {

        //은행 계좌를 등록해야하는 서비스 (비즈니스 로직)

        // (멤버 서비스도 확인?) 여기서는 skip

        // 1. 등록된 계좌인지 확인한다.
        // 외부의 은행에 이 계좌 정상인지? 확인을 해야해요.
        // Biz Logic -> External System
        // Port -> Adapter -> External System
        // Port
        //실제 외부의 은행계좌 정보를 Get
        BankAccount accountInfo = requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber()));
        boolean accountIsValid = accountInfo.isValid();

        // 2. 등록가능한 계좌라면, 등록한다. 성공하면, 등록에 성공한 등록 정보를 리턴
        // 2-1. 등록가능하지 않은 계좌라면. 에러를 리턴
        if (accountIsValid) {

            RegisteredBankAccountJpaEntity savedAccountInfo = registerBankAccountPort.createRegisteredBankAccount(
                    new MembershipId(command.getMembershipId() + ""),
                    new BankName(command.getBankName()),
                    new BankAccountNumber(command.getBankAccountNumber()),
                    new LinkedStatusIsValid(command.isValid())
            );

            return mapper.mapToDomainEntity(savedAccountInfo);
        } else {
            return null;
        }
    }
}
