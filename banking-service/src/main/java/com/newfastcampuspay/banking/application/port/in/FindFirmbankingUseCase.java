package com.newfastcampuspay.banking.application.port.in;

import com.newfastcampuspay.banking.domain.FirmbankingRequest;

public interface FindFirmbankingUseCase {
    FirmbankingRequest findFirmbankingRequest(FindFirmbankingCommand command);
}
