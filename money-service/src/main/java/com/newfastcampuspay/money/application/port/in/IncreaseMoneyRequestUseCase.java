package com.newfastcampuspay.money.application.port.in;

import com.newfastcampuspay.money.domain.MoneyChangingRequest;

/**
 * 구현체 : service
 */
public interface IncreaseMoneyRequestUseCase {

    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);

    MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command);

    void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command);
}
