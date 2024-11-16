package com.newfastcampuspay.banking.adapter.out.external.bank;

import com.newfastcampuspay.banking.adapter.out.persistence.SpringDataRegisteredBankAccountRepository;
import com.newfastcampuspay.banking.application.port.out.RequestBankAccountInfoPort;
import com.newfastcampuspay.common.ExternalSystemAdapter;
import lombok.RequiredArgsConstructor;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort {

    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {
        //실제로 외부 은행에 http을 통해서
        //실제 은행 계좌 정보를 가져오고

        //실제 은행 계좌 -> BankAccount

        return new BankAccount(request.getBankName(), request.getBankAccountNumber(), true);
    }
}
