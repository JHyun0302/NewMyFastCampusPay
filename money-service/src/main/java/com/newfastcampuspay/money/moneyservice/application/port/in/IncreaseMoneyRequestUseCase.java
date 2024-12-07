package com.newfastcampuspay.money.moneyservice.application.port.in;

import com.newfastcampuspay.money.moneyservice.domain.MoneyChangingRequest;

/**
 * 구현체 : service
 */
public interface IncreaseMoneyRequestUseCase {

    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);
}
