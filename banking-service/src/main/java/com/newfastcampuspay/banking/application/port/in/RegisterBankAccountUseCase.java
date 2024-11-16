package com.newfastcampuspay.banking.application.port.in;

import com.newfastcampuspay.banking.domain.RegisteredBankAccount;

/**
 * 구현체 : service
 */
public interface RegisterBankAccountUseCase {

    RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command);
}
