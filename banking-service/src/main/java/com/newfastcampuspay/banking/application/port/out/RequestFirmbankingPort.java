package com.newfastcampuspay.banking.application.port.out;

import com.newfastcampuspay.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import com.newfastcampuspay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.newfastcampuspay.banking.domain.FirmbankingRequest;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount;

public interface RequestFirmbankingPort {
    FirmbankingRequestJpaEntity createFirmbankingRequest(
            FirmbankingRequest.FromBankName fromBankName,
            FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber,
            FirmbankingRequest.ToBankName toBankName,
            FirmbankingRequest.ToBankAccountNumber toBankAccountNumber,
            FirmbankingRequest.MoneyAmount moneyAmount,
            FirmbankingRequest.FirmbankingStatus firmbankingStatus
    );

    FirmbankingRequestJpaEntity modifyFirmbankingRequest(
            FirmbankingRequestJpaEntity entity
    );
}
