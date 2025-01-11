package com.newfastcampuspay.banking.adapter.in.web;

import com.newfastcampuspay.banking.application.port.in.RequestFirmbankingCommand;
import com.newfastcampuspay.banking.application.port.in.RequestFirmbankingUseCase;
import com.newfastcampuspay.banking.application.port.in.UpdateFirmbankingCommand;
import com.newfastcampuspay.banking.application.port.in.UpdateFirmbankingUseCase;
import com.newfastcampuspay.banking.domain.FirmbankingRequest;
import com.newfastcampuspay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {

    private final RequestFirmbankingUseCase requestFirmbankingUseCase;

    private final UpdateFirmbankingUseCase updateFirmbankingUseCase;

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

    @PostMapping("/banking/firmbanking/request-eda")
    void registerFirmbankingByEvent(@RequestBody RequestFirmbankingRequest request) {
        //request -> command (Port.in)
        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();

        requestFirmbankingUseCase.requestFirmbankingByEvent(command);
    }

    @PutMapping("/banking/firmbanking/update-eda")
    void updateFirmbankingByEvent(@RequestBody UpdateFirmbankingRequest request) {
        //request -> command (Port.in)
        UpdateFirmbankingCommand command = UpdateFirmbankingCommand.builder()
                .firmbankingAggregateIdentifier(request.getFirmbankingRequestAggregateIdentifier())
                .firmbankingStatus(request.getStatus())
                .build();

        updateFirmbankingUseCase.updateFirmbankingByEvent(command);
    }

}
