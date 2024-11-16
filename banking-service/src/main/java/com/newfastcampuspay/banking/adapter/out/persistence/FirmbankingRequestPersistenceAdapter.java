package com.newfastcampuspay.banking.adapter.out.persistence;

import com.newfastcampuspay.banking.application.port.out.RegisterBankAccountPort;
import com.newfastcampuspay.banking.application.port.out.RequestFirmbankingPort;
import com.newfastcampuspay.banking.domain.FirmbankingRequest;
import com.newfastcampuspay.banking.domain.FirmbankingRequest.FirmbankingStatus;
import com.newfastcampuspay.banking.domain.FirmbankingRequest.FromBankAccountNumber;
import com.newfastcampuspay.banking.domain.FirmbankingRequest.FromBankName;
import com.newfastcampuspay.banking.domain.FirmbankingRequest.MoneyAmount;
import com.newfastcampuspay.banking.domain.FirmbankingRequest.ToBankAccountNumber;
import com.newfastcampuspay.banking.domain.FirmbankingRequest.ToBankName;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.BankAccountNumber;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.BankName;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.LinkedStatusIsValid;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.MembershipId;
import com.newfastcampuspay.common.PersistenceAdapter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class FirmbankingRequestPersistenceAdapter implements RequestFirmbankingPort {

    private final SpringDataFirmbankingRequestRepository firmbankingRequestRepository;


    @Override
    public FirmbankingRequestJpaEntity createFirmbankingRequest(FirmbankingRequest.FromBankName fromBankName, FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber, FirmbankingRequest.ToBankName toBankName, FirmbankingRequest.ToBankAccountNumber toBankAccountNumber, FirmbankingRequest.MoneyAmount moneyAmount, FirmbankingRequest.FirmbankingStatus firmbankingStatus) {
        return firmbankingRequestRepository.save(new FirmbankingRequestJpaEntity(
                fromBankName.getFromBankName(),
                fromBankAccountNumber.getFromBankAccountNumber(),
                toBankName.getToBankName(),
                toBankAccountNumber.getToBankAccountNumber(),
                moneyAmount.getMoneyAmount(),
                firmbankingStatus.getFirmbankingStatus(),
                UUID.randomUUID()
        ));
    }

    @Override
    public FirmbankingRequestJpaEntity modifyFirmbankingRequest(FirmbankingRequestJpaEntity entity) {
        return firmbankingRequestRepository.save(entity);
    }
}
