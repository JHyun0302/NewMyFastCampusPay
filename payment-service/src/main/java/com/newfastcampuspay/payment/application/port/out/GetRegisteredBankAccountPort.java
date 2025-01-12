package com.newfastcampuspay.payment.application.port.out;

public interface GetRegisteredBankAccountPort {

    RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId);

}
