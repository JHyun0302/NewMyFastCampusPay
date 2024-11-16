package com.newfastcampuspay.banking.adapter.out.persistence;

import com.newfastcampuspay.banking.domain.FirmbankingRequest;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class FirmbankingRequestMapper {
    public FirmbankingRequest mapToDomainEntity(FirmbankingRequestJpaEntity requestFirmbankingJpaEntity, UUID uuid) {
        return FirmbankingRequest.generateFirmbankingRequest(
                new FirmbankingRequest.FirmbankingRequestId(requestFirmbankingJpaEntity.getRequestFirmbankingId() + ""),
                new FirmbankingRequest.FromBankName(requestFirmbankingJpaEntity.getFromBankName()),
                new FirmbankingRequest.FromBankAccountNumber(requestFirmbankingJpaEntity.getFromBankAccountNumber()),
                new FirmbankingRequest.ToBankName(requestFirmbankingJpaEntity.getToBankName()),
                new FirmbankingRequest.ToBankAccountNumber(requestFirmbankingJpaEntity.getToBankAccountNumber()),
                new FirmbankingRequest.MoneyAmount(requestFirmbankingJpaEntity.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(requestFirmbankingJpaEntity.getFirmbankingStatus()),
                uuid
        );
    }
}
