package com.newfastcampuspay.banking.adapter.out.external.bank;

import com.newfastcampuspay.banking.application.port.out.RequestBankAccountInfoPort;
import com.newfastcampuspay.banking.application.port.out.RequestExternalFirmbanking;
import com.newfastcampuspay.common.ExternalSystemAdapter;
import lombok.RequiredArgsConstructor;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort, RequestExternalFirmbanking {
    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {
        // 실제로 외부 은행에 http 를 통해 실테 은행 계좌 정보를 가져오고

        // 실제 은행 계좌 -> BankAccount return
        return new BankAccount(request.getBankName(), request.getBankAccountNumber(), true);
    }

    @Override
    public FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request) {
        // 실제로 외부 은행에 http 통신을 통해서
        // 펌뱅킹 요청을 하고

        // 그 결과를
        // 외부 은행의 실제 결과를 -> 패캠 페이의 FirmbankingResult 파싱
        return new FirmbankingResult(0);
    }
}
