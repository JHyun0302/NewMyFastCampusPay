package com.newfastcampuspay.banking.application.port.in;

/**
 * 구현체 : service
 */
public interface UpdateFirmbankingUseCase {

    void updateFirmbankingByEvent(UpdateFirmbankingCommand command);
}
