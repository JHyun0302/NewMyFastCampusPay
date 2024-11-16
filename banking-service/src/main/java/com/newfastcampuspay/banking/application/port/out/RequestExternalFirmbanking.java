package com.newfastcampuspay.banking.application.port.out;

import com.newfastcampuspay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.newfastcampuspay.banking.adapter.out.external.bank.FirmbankingResult;

public interface RequestExternalFirmbanking {
    FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request);
}
