package com.newfastcampuspay.banking.application.port.out;

import com.newfastcampuspay.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import com.newfastcampuspay.banking.domain.FirmbankingRequest;

public interface FindFirmbankingPort {
    FirmbankingRequestJpaEntity findFirmbanking(
            FirmbankingRequest.FirmbankingRequestId firmbankingRequestId
    );
}
