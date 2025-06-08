package com.newfastcampuspay.money.application.service;

import com.newfastcampuspay.common.CountDownLatchManager;
import com.newfastcampuspay.common.RechargingMoneyTask;
import com.newfastcampuspay.common.SubTask;
import com.newfastcampuspay.common.UseCase;
import com.newfastcampuspay.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.newfastcampuspay.money.adapter.axon.command.RechargingMoneyRequestCreateCommand;
import com.newfastcampuspay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.newfastcampuspay.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.newfastcampuspay.money.application.port.in.CreateMemberMoneyCommand;
import com.newfastcampuspay.money.application.port.in.CreateMemberMoneyUseCase;
import com.newfastcampuspay.money.application.port.in.CreatedMemberMoneyPort;
import com.newfastcampuspay.money.application.port.in.GetMemberMoneyPort;
import com.newfastcampuspay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.newfastcampuspay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.newfastcampuspay.money.application.port.out.GetMembershipPort;
import com.newfastcampuspay.money.application.port.out.IncreaseMoneyPort;
import com.newfastcampuspay.money.application.port.out.SendRechargingMoneyTaskPort;
import com.newfastcampuspay.money.domain.MemberMoney;
import com.newfastcampuspay.money.domain.MoneyChangingRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase, CreateMemberMoneyUseCase {

    private final CountDownLatchManager countDownLatchManager;

    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;

    private final GetMembershipPort membershipPort;

    private final IncreaseMoneyPort increaseMoneyPort;

    private final MoneyChangingRequestMapper mapper;

    private final CommandGateway commandGateway;

    private final CreatedMemberMoneyPort createdMemberMoneyPort;

    private final GetMemberMoneyPort getMemberMoneyPort;

    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {
        // 머니의 충전. 증액이라는 과정
        // 1. 고객 정보가 정상인지 확인 (멤버)
        membershipPort.getMembership(command.getTargetMembershipId());

        // 2. 고객의 연동된 계좌가 있는지, 고객의 연동된 계좌의 잔액이 충분한지도 확인 (뱅킹)

        // 3. 법인 계좌 상태도 정상인지 확인 (뱅킹)

        // 4. 증액을 위한 "기록". 요청 상태로 MoneyChangingRequest 를 생성 (MoneyChangingRequest)

        // 5. 펌뱅킹을 수행하고 (고객의 연동된 계좌 -> 패캠페이 법인 계좌) (뱅킹)

        // 6-1. 결과가 정상적이라면. 성공으로 MoneyChangingRequest 상태값을 변동 후에 리턴
        //성공 시에 멤버의 MemberMoney 값 증액이 필요
        MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId()), command.getAmount());

        if (memberMoneyJpaEntity != null) {
            return mapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                            new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                            new MoneyChangingRequest.MoneyChangingType(0),
                            new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                            new MoneyChangingRequest.MoneyChangingStatus(1),
                            new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
                    )
            );
        }

        // 6-2. 결과가 실패라면. 실패라고 MoneyChangingRequest 상태값을 변동 후에 리턴

        return null;
    }

    @Override
    public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {

        // SubTask : 각 서비스에 특정 membershipId로 Validation 하기 위한 Task.

        // 1. Subtask, Task
        SubTask validMemberTask = SubTask.builder()
                .subTaskName("validMemberTask : " + "멤버십 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();

        // Banking Sub task에는 아래와 같은 유효성 검사도 해야함. 하지만 위의 정의와 같이 간소화한 MVP 버전을 만든다.
        // Baking Account Validation
        SubTask validBankingAccountTask = SubTask.builder()
                .subTaskName("validBankingAccountTask : " + "뱅킹 계좌 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("banking")
                .status("ready")
                .build();

        // Amount Money Firmbanking --> 무조건 ok 받았다고 가정.

        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(validMemberTask);
        subTaskList.add(validBankingAccountTask);

        RechargingMoneyTask task = RechargingMoneyTask.builder()
                .taskID(UUID.randomUUID().toString())
                .taskName("Increase Money Task / 머니 충전 Task")
                .subTaskList(subTaskList)
                .moneyAmount(command.getAmount())
                .membershipId(command.getTargetMembershipId())
                .toBankName("fastcampus")
                .build();

        /**
         * 2. Kafka Cluster Produce
         */
        // Task Produce
        sendRechargingMoneyTaskPort.sendRechargingMoneyTaskPort(task);
        countDownLatchManager.addCountDownLatch(task.getTaskID());

        /**
         *  3. Wait : Thread wait
         */
        try {
            countDownLatchManager.getCountDownLatch(task.getTaskID()).await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 3-1. task-consumer
        // 등록된 sub-task, status 모두 ok -> task 결과를 Produce

        /**
         * 4. Task Result Consume
         * 4번부터 이어지는 로직은 최소 3초 이후!
         */
        // 받은 응답을 다시, countDownLatchManger 를 통해서 결과 데이터를 받아야 함. -> 그래서 RechargingMoneyResultConsumer 에서 .setDataForKey 해줌.
        String result = countDownLatchManager.getDataForKey(task.getTaskID());
        if (result.equals("success")) {
            // 4-1. Consume ok, Logic
            MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                    new MemberMoney.MembershipId(command.getTargetMembershipId()), command.getAmount());

            if (memberMoneyJpaEntity != null) {
                return mapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                                new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                                new MoneyChangingRequest.MoneyChangingType(0),
                                new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                                new MoneyChangingRequest.MoneyChangingStatus(1),
                                new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
                        )
                );
            }
        } else {
            // 4-2. Consume fail, Logic
            return null;
        }

        // 5. Consume ok, Logic

        return null;
    }

    @Override
    public void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command) {

        MemberMoneyJpaEntity memberMoneyJpaEntity = getMemberMoneyPort.getMemberMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId())
        );

        String memberMoneyAggregateIdentifier = memberMoneyJpaEntity.getAggregateIdentifier();

        //Saga 의 시작을 나타내는 커맨드!
        //RechargingMoneyRequestCreateCommand
        commandGateway.send(new RechargingMoneyRequestCreateCommand(memberMoneyAggregateIdentifier,
                UUID.randomUUID().toString(), command.getTargetMembershipId(), command.getAmount()))
                .whenComplete(
                (result, throwable) -> {
                    if (throwable != null) {
                        log.error("throwable : {}", throwable);
                        throw new RuntimeException(throwable);
                    } else {
                        // Increase money -> money increase
                        log.info("increaseMoney result : {}", result);
                        increaseMoneyPort.increaseMoney(
                                new MemberMoney.MembershipId(command.getTargetMembershipId()), command.getAmount());
                    }
                }
        );

        /**
         * saga 등록되기 전
         */
//        MemberMoneyJpaEntity memberMoneyJpaEntity = getMemberMoneyPort.getMemberMoney(
//                new MemberMoney.MembershipId(command.getTargetMembershipId())
//        );
//
//        String aggregateIdentifier = memberMoneyJpaEntity.getAggregateIdentifier();
//
//        //command
//        commandGateway.send(IncreaseMemberMoneyCommand.builder()
//                        .aggregateIdentifier(aggregateIdentifier)
//                        .membershipId(command.getTargetMembershipId())
//                        .amount(command.getAmount()).build())
//                .whenComplete((result, throwable) -> {
//                    if (throwable != null) {
//                        log.error("throwable : {}", throwable);
//                        throw new RuntimeException(throwable);
//                    } else {
//                        // Increase money -> money increase
//                        log.info("increaseMoney result : {}", result);
//                        increaseMoneyPort.increaseMoney(
//                                new MemberMoney.MembershipId(command.getTargetMembershipId()), command.getAmount());
//                    }
//                });
    }

    @Override
    public void createMemberMoney(CreateMemberMoneyCommand command) {
        /**
         * Axon Framework에서 Axon Server로 보내기
         * 신뢰성, 가용성 매우 높은 구간
         */
        MemberMoneyCreatedCommand axonCommand = new MemberMoneyCreatedCommand(command.getMembershipId());
        /**
         * Event Sourcing의 결과 대기 및 확인
         */
        commandGateway.send(axonCommand).whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.error("throwable : {}", throwable);
                throw new RuntimeException(throwable);
            } else {
                log.info("result : {}", result);
                createdMemberMoneyPort.createMemberMoney(
                        new MemberMoney.MemberMoneyId(command.getMembershipId()),
                        new MemberMoney.MoneyAggregateIdentifier(result.toString())
                );
            }
        });
    }
}
