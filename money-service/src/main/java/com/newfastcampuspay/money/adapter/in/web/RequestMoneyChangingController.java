package com.newfastcampuspay.money.adapter.in.web;

import com.newfastcampuspay.common.WebAdapter;
import com.newfastcampuspay.money.application.port.in.CreateMemberMoneyCommand;
import com.newfastcampuspay.money.application.port.in.CreateMemberMoneyUseCase;
import com.newfastcampuspay.money.application.port.in.DecreaseMoneyRequestCommand;
import com.newfastcampuspay.money.application.port.in.DecreaseMoneyRequestUseCase;
import com.newfastcampuspay.money.application.port.in.FindMemberMoneyListByMembershipIdsCommand;
import com.newfastcampuspay.money.application.port.in.FindMemberMoneyListByMembershipIdsRequestUseCase;
import com.newfastcampuspay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.newfastcampuspay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.newfastcampuspay.money.domain.MemberMoney;
import com.newfastcampuspay.money.domain.MoneyChangingRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;

    private final DecreaseMoneyRequestUseCase decreaseMoneyRequestUseCase;

    private final CreateMemberMoneyUseCase createMemberMoneyUseCase;

    private final FindMemberMoneyListByMembershipIdsRequestUseCase findMemberMoneyListByMembershipIdsRequestUseCase;

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

    @PostMapping("/money/increase-async")
    MoneyChangingResultDetail increaseMoneyChangingRequestAsync(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequestAsync(command);

        // MoneyChangingRequest -> MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0, 0, moneyChangingRequest.getChangingMoneyAmount());

        return resultDetail;
    }

    @PostMapping("/money/decrease-eda")
    MoneyChangingResultDetail decreaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {
        DecreaseMoneyRequestCommand command = DecreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount() * -1) // -1 곱하면 감액 api
                .build();

        MoneyChangingRequest moneyChangingRequest = decreaseMoneyRequestUseCase.decreaseMoneyRequest(command);
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                1, 0, moneyChangingRequest.getChangingMoneyAmount());

        return resultDetail;
    }

    /**
     * 고객의 머니 정보를 Axon Framework를 통해 Create 하기
     */
    @PostMapping("/money/create-member-money")
    void createMemberMoney(@RequestBody CreateMemberMoneyRequest request) {
        createMemberMoneyUseCase.createMemberMoney(
                CreateMemberMoneyCommand.builder().membershipId(request.getTargetMembershipId()).build());
    }

    /**
     * DB에서 identifier를 통해 해당 event를 찾아 머니 증감
     */
    @PostMapping("/money/increase-eda")
    void increaseMoneyChangingRequestByEvent(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        increaseMoneyRequestUseCase.increaseMoneyRequestByEvent(command);
    }

    @PostMapping("/money/member-money")
    List<MemberMoney> findMemberMoneyListByMembershipIds(@RequestBody FindMemberMoneyListByMembershipIdsRequest request) {
        FindMemberMoneyListByMembershipIdsCommand command = FindMemberMoneyListByMembershipIdsCommand.builder()
                .membershipIds(request.getMembershipIds())
                .build();

        return findMemberMoneyListByMembershipIdsRequestUseCase.findMemberMoneyListByMembershipIds(command);
    }

}
