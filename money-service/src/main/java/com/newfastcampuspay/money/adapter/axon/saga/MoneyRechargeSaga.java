package com.newfastcampuspay.money.adapter.axon.saga;

import com.newfastcampuspay.common.event.CheckRegisteredBankAccountCommand;
import com.newfastcampuspay.common.event.CheckedRegisteredBankAccountEvent;
import com.newfastcampuspay.common.event.RequestFirmbankingCommand;
import com.newfastcampuspay.common.event.RequestFirmbankingFinishedEvent;
import com.newfastcampuspay.common.event.RollbankFirmbankingRequestCommand;
import com.newfastcampuspay.money.adapter.axon.event.RechargingRequestCreatedEvent;
import com.newfastcampuspay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.newfastcampuspay.money.application.port.out.IncreaseMoneyPort;
import com.newfastcampuspay.money.domain.MemberMoney.MembershipId;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MoneyRechargeSaga : 어떤 상황에서 어떤 동작을 해야하는지 판단하는 주체
 */
@Slf4j
@Saga
@NoArgsConstructor
public class MoneyRechargeSaga {

    @NonNull
    private transient CommandGateway commandGateway;

    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    /**
     * 등록된 은행 계좌인지 체크를 위해 외부 은행과 통신
     * associateProperty : 여러 saga가 존재할 때, 메모리에서 saga를 구분하기 위한 구분자
     *  - rechargingRequestId : 1개의 충전 요청 saga에 대해 1개만 생성 (PK)
     */
    @StartSaga
    @SagaEventHandler(associationProperty = "rechargingRequestId")
    public void handle(RechargingRequestCreatedEvent event) {
        log.info("RechargingRequestCreatedEvent Start saga");
        String rechargingRequestId = event.getRechargingRequestId();

        /**
         * 이전까지는 associatedProperty의 구분자를 이용했고
         * 앞으로 handle 동작에서는 어떤 것을 associate Key로 활용할지 정의
         */
        String checkRegisteredBankAccountId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("checkRegisteredBankAccountId", rechargingRequestId);

        // "충전 요청"이 시작되었다.

        // 뱅킹의 계좌 등록 여부 확인하기. (RegisteredBankAccountAggregate 활용해서..)
        // CheckRegisteredBankAccountCommand -> Check Bank Account
        // Axon server를 통해 Money svc -> Banking svc 호출. 즉, money 뿐만 아니라 다른 서비스에서도 이용하므로 Common에 Event 정의!

        // 기본적으로 axon framework에서, 모든 aggregate의 변경은, aggregate 단위로 되어야만 한다.
        commandGateway.send(new CheckRegisteredBankAccountCommand(
                event.getRegisteredBankAccountAggregateIdentifier(),
                event.getRechargingRequestId(),
                checkRegisteredBankAccountId,
                event.getMembershipId(),
                event.getBankName(),
                event.getBankAccountNumber(),
                event.getAmount())
        ).whenComplete(
                (result, throwable) -> {
                    if (throwable != null) {
                        log.error("CheckRegisteredBankAccountCommand Command failed : {}", throwable);
                        throw new RuntimeException(throwable);
                    } else {
                        log.info("CheckRegisteredBankAccountCommand Command success");
                    }
                }
        );
    }

    /**
     * 외부 은행 응답에 따라 펌배뱅킹(송금) 요청 개시
     */
    @SagaEventHandler(associationProperty = "checkRegisteredBankAccountId")
    public void handle(CheckedRegisteredBankAccountEvent event) {
        log.info("CheckRegisteredBankAccountEvent saga : {}", event.toString());
        boolean status = event.isChecked();
        if (status) {
            log.info("CheckedRegisteredBankAccountEvent event Success");
        } else {
            log.error("CheckedRegisteredBankAccountEvent event Failed");
        }

        String requestFirmbankingId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("requestFirmbankingId", requestFirmbankingId);

        // 송금 요청
        // 고객 계좌 -> 법인 계좌
        commandGateway.send(new RequestFirmbankingCommand(
                requestFirmbankingId,
                event.getFirmbankingRequestAggregateIdentifier(),
                event.getRechargingRequestId(),
                event.getMembershipId(),
                event.getFromBankName(), // 고객 계좌
                event.getFromBankAccountNumber(), // 고객 계좌 번호
                "fastcampus", //법인 계좌
                "123456789", //법인 계좌 번호
                event.getAmount()
        )).whenComplete(
                (result, throwable) -> {
                    if (throwable != null) {
                        log.error("RequestFirmbankingCommand Command failed : {}", throwable);
                        throw new RuntimeException(throwable);
                    } else {
                        log.info("RequestFirmbankingCommand Command success");
                    }
                }
        );
    }

    /**
     * 펌뱅킹 요청 수행
     * - 성공 시, 머니 증액 (DB Update)
     * - 실패 시, Rollback Saga
     */
    @SagaEventHandler(associationProperty = "requestFirmbankingId")
    public void handle(RequestFirmbankingFinishedEvent event, IncreaseMoneyPort increaseMoneyPort) {
        log.info("RequestFirmbankingFinishedEvent saga : {}", event.toString());
        boolean status = event.getStatus() == 0;
        if (status) {
            log.info("RequestFirmbankingFinishedEvent event Success");
        } else {
            log.error("RequestFirmbankingFinishedEvent event Failed");
        }

        //DB Update 명령
        MemberMoneyJpaEntity resultEntity = increaseMoneyPort.increaseMoney(
                new MembershipId(event.getMembershipId()), event.getMoneyAmount());

        //DB Update 실패 -> 보상 트랜잭션(롤백) 수행
//        MemberMoneyJpaEntity resultEntity = null;

        if (resultEntity == null) {
            //실패 시, 롤백 이벤트
            String rollbackFirmbankingId = UUID.randomUUID().toString();
            SagaLifecycle.associateWith("rollbackFirmbankingId", rollbackFirmbankingId);
            commandGateway.send(new RollbankFirmbankingRequestCommand(
                    rollbackFirmbankingId,
                    event.getRequestFirmbankingAggregateIdentifier(),
                    event.getRechargingRequestId(),
                    event.getMembershipId(),
                    event.getToBankName(),
                    event.getToBankAccountNumber(),
                    event.getMoneyAmount()
            )).whenComplete(
                    (result, throwable) -> {
                        if (throwable != null) {
                            log.error("RollbankFirmbankingRequestCommand Command failed : {}", throwable);
                            throw new RuntimeException(throwable);
                        } else {
                            log.info("RollbankFirmbankingRequestCommand Command success : {}", result.toString());
                            SagaLifecycle.end();
                        }
                    }
            );
        } else {
            //성공 시, saga 종료
            SagaLifecycle.end();
        }
    }

    // 롤백 완료 시, Saga 끝
    @EndSaga
    @SagaEventHandler(associationProperty = "rollbackFirmbankingId")
    public void handle(RollbankFirmbankingRequestCommand event) {
        log.info("RollbankFirmbankingFinishedEvent saga : {}", event.toString());
    }

}
