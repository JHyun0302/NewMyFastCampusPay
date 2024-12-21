package com.newfastcampuspay.remittance.adapter.out.service.banking;

import com.newfastcampuspay.common.CommonHttpClient;
import com.newfastcampuspay.common.ExternalSystemAdapter;
import com.newfastcampuspay.remittance.application.port.out.banking.BankingInfo;
import com.newfastcampuspay.remittance.application.port.out.banking.BankingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankingServiceAdapter implements BankingPort {

    private final CommonHttpClient bankingServiceHttpClient;

    @Value("${service.banking.url")
    private String moneyServiceEndpoint;


    @Override
    public BankingInfo getMembershipBankingInfo(String bankName, String bankAccountNumber) {
        return null;
    }

    @Override
    public boolean requestFirmbanking(String bankName, String bankAccountNumber, int amount) {
        return false;
    }
}
