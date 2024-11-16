package com.newfastcampuspay.banking.application.port.in;

import com.newfastcampuspay.banking.domain.FirmbankingRequest;

/**
 * 구현체 : service
 */
public interface RequestFirmbankingUseCase {

    FirmbankingRequest requestFirmbanking(RequestFirmbankingCommand command);
}
