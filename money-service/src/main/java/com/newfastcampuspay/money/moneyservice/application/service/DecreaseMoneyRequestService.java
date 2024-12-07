package com.newfastcampuspay.money.moneyservice.application.service;

import com.newfastcampuspay.common.UseCase;
import com.newfastcampuspay.money.moneyservice.adapter.out.persistence.MemberMoneyJpaEntity;
import com.newfastcampuspay.money.moneyservice.adapter.out.persistence.MoneyChangingRequestMapper;
import com.newfastcampuspay.money.moneyservice.application.port.in.DecreaseMoneyRequestCommand;
import com.newfastcampuspay.money.moneyservice.application.port.in.DecreaseMoneyRequestUseCase;
import com.newfastcampuspay.money.moneyservice.application.port.in.IncreaseMoneyRequestCommand;
import com.newfastcampuspay.money.moneyservice.application.port.in.IncreaseMoneyRequestUseCase;
import com.newfastcampuspay.money.moneyservice.application.port.out.DecreaseMoneyPort;
import com.newfastcampuspay.money.moneyservice.application.port.out.IncreaseMoneyPort;
import com.newfastcampuspay.money.moneyservice.domain.MemberMoney;
import com.newfastcampuspay.money.moneyservice.domain.MoneyChangingRequest;
import com.newfastcampuspay.money.moneyservice.domain.MoneyChangingRequest.Uuid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class DecreaseMoneyRequestService implements DecreaseMoneyRequestUseCase {

    private final DecreaseMoneyPort decreaseMoneyPort;

    private final MoneyChangingRequestMapper mapper;

    @Override
    public MoneyChangingRequest decreaseMoneyRequest(DecreaseMoneyRequestCommand command) {
        // 머니의 충전. 증액이라는 과정
        // 1. 고객 정보가 정상인지 확인 (멤버)
//        membershipPort.getMembership(command.getTargetMembershipId());

        // 2. 고객의 연동된 계좌가 있는지, 고객의 연동된 계좌의 잔액이 충분한지도 확인 (뱅킹)

        // 3. 법인 계좌 상태도 정상인지 확인 (뱅킹)

        // 4. 증액을 위한 "기록". 요청 상태로 MoneyChangingRequest 를 생성 (MoneyChangingRequest)

        // 5. 펌뱅킹을 수행하고 (고객의 연동된 계좌 -> 패캠페이 법인 계좌) (뱅킹)

        // 6-1. 결과가 정상적이라면. 성공으로 MoneyChangingRequest 상태값을 변동 후에 리턴
        //성공 시에 멤버의 MemberMoney 값 증액이 필요
        MemberMoneyJpaEntity memberMoneyJpaEntity = decreaseMoneyPort.decreaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId()), command.getAmount());

        if (memberMoneyJpaEntity != null) {
            return mapper.mapToDomainEntity(decreaseMoneyPort.createMoneyChangingRequest(
                            new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                            new MoneyChangingRequest.MoneyChangingType(0),
                            new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                            new MoneyChangingRequest.MoneyChangingStatus(1),
                            new Uuid(UUID.randomUUID().toString())
                    )
            );
        }

        // 6-2. 결과가 실패라면. 실패라고 MoneyChangingRequest 상태값을 변동 후에 리턴

        return null;
    }
}
