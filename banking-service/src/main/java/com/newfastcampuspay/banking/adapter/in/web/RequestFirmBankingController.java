package com.newfastcampuspay.banking.adapter.in.web;

import com.newfastcampuspay.banking.application.port.in.RequestFirmbankingCommand;
import com.newfastcampuspay.banking.application.port.in.RegisterBankAccountCommand;
import com.newfastcampuspay.banking.application.port.in.RequestFirmbankingUseCase;
import com.newfastcampuspay.banking.domain.FirmbankingRequest;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount;
import com.newfastcampuspay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmBankingController {

    private final RequestFirmbankingUseCase requestFirmbankingUseCase;

    @PostMapping("/banking/firmbanking/request")
    FirmbankingRequest registerMembership(@RequestBody RequestFirmbankingRequest request) {
        //request -> command (Port.in)
        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();

        return requestFirmbankingUseCase.requestFirmbanking(command);
    }
}
