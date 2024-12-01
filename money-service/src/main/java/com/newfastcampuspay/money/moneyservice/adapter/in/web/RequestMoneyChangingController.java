package com.newfastcampuspay.money.moneyservice.adapter.in.web;

import com.newfastcampuspay.common.WebAdapter;
import com.newfastcampuspay.money.moneyservice.application.port.in.IncreaseMoneyRequestCommand;
import com.newfastcampuspay.money.moneyservice.application.port.in.IncreaseMoneyRequestUseCase;
import com.newfastcampuspay.money.moneyservice.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;
//    private final DecreaseMoneyRequestUseCase decreaseMoneyRequestUseCase;

    @PostMapping("/money/increase")
    MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequest(command);

        // MoneyChangingRequest -> MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0, 0, moneyChangingRequest.getChangingMoneyAmount());

        return resultDetail;
    }
    @PostMapping("/money/decrease")
    MoneyChangingResultDetail decreaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {
        //request -> command (Port.in)
//        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
//                .membershipId(request.getMembershipId())
//                .bankName(request.getBankName())
//                .bankAccountNumber(request.getBankAccountNumber())
//                .isValid(request.isValid())
//                .build();

        //registeredBankAccountUseCase.registerBankAccount(command);
        // -> MoneyChangingResultDetail
        //return decreaseMoneyRequestUseCase.decreaseMoneyChangingRequest(command);
        return null;
    }
}
