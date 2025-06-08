package com.newfastcampuspay.money.adapter.out.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newfastcampuspay.common.CommonHttpClient;
import com.newfastcampuspay.money.application.port.out.GetRegisteredBankAccountPort;
import com.newfastcampuspay.money.application.port.out.RegisteredBankAccountAggregateIdentifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BankingServiceAdapter implements GetRegisteredBankAccountPort {

    private final CommonHttpClient commonHttpClient;

    private final String bankingServiceUrl;

    public BankingServiceAdapter(CommonHttpClient commonHttpClient,
                                 @Value("${service.banking.url}") String bankingServiceUrl) {
        this.commonHttpClient = commonHttpClient;
        this.bankingServiceUrl = bankingServiceUrl;
    }

    @Override
    public RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId) {
        //FindBankAccountController.findBankAccount()
        String url = String.join("/", bankingServiceUrl, "banking/account", membershipId);
        try {
            String jsonsResponse = commonHttpClient.sendGetRequest(url).body();
            //json RegisteredBankAccount
            ObjectMapper mapper = new ObjectMapper();
            RegisteredBankAccount registeredBankAccount = mapper.readValue(jsonsResponse, RegisteredBankAccount.class);

            return new RegisteredBankAccountAggregateIdentifier(
                    registeredBankAccount.getRegisteredBankAccountId(),
                    registeredBankAccount.getAggregateIdentifier(),\
                    registeredBankAccount.getMembershipId(),
                    registeredBankAccount.getBankName(),
                    registeredBankAccount.getBankAccountNumber()
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
