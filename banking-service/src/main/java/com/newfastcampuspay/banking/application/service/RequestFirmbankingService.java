package com.newfastcampuspay.banking.application.service;

import com.newfastcampuspay.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import com.newfastcampuspay.banking.adapter.axon.command.UpdateFirmbankingRequestCommand;
import com.newfastcampuspay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.newfastcampuspay.banking.adapter.out.external.bank.FirmbankingResult;
import com.newfastcampuspay.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import com.newfastcampuspay.banking.adapter.out.persistence.FirmbankingRequestMapper;
import com.newfastcampuspay.banking.application.port.in.RequestFirmbankingCommand;
import com.newfastcampuspay.banking.application.port.in.RequestFirmbankingUseCase;
import com.newfastcampuspay.banking.application.port.in.UpdateFirmbankingCommand;
import com.newfastcampuspay.banking.application.port.in.UpdateFirmbankingUseCase;
import com.newfastcampuspay.banking.application.port.out.RequestExternalFirmbanking;
import com.newfastcampuspay.banking.application.port.out.RequestFirmbankingPort;
import com.newfastcampuspay.banking.domain.FirmbankingRequest;
import com.newfastcampuspay.banking.domain.FirmbankingRequest.FirmbankingAggregateIdentifier;
import com.newfastcampuspay.common.UseCase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase, UpdateFirmbankingUseCase {

    private final FirmbankingRequestMapper mapper;

    private final RequestFirmbankingPort requestFirmbankingPort;

    private final RequestExternalFirmbanking requestExternalFirmbanking;

    private final CommandGateway commandGateway;

    @Override
    public FirmbankingRequest requestFirmbanking(RequestFirmbankingCommand command) {
        // Business Logic
        // a -> b 계좌

        // 1. 요청에 대한 정보를 먼저 write. "요청" 상태로
        FirmbankingRequestJpaEntity requestedEntity = requestFirmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.FromBankName(command.getFromBankName()),
                new FirmbankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmbankingRequest.ToBankName(command.getToBankName()),
                new FirmbankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(0),
                new FirmbankingAggregateIdentifier("")
        );

        // 2. 외부 은행에 펌뱅킹 요청
        FirmbankingResult result = requestExternalFirmbanking.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber(),
                command.getMoneyAmount()
        ));

        //Transactional UUID
        UUID randomUUID = UUID.randomUUID();
        requestedEntity.setUuid(randomUUID.toString());

        // 3. 결과에 따라서 1번에서 작성했던 FirmbankingRequest 정보를 Update
        if (result.getResultCode() == 0) {
            // 성공
            requestedEntity.setFirmbankingStatus(1);
        } else {
            // 실패
            requestedEntity.setFirmbankingStatus(2);
        }

        // 4. 결과를 리턴하기 전에 바뀐 상태  값을 기준으로 다시 save
        return mapper.mapToDomainEntity(requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity), randomUUID);
    }

    @Override
    public void requestFirmbankingByEvent(RequestFirmbankingCommand command) {
        CreateFirmbankingRequestCommand createFirmbankingRequestCommand = CreateFirmbankingRequestCommand.builder()
                .toBankName(command.getToBankName())
                .toBankAccountNumber(command.getToBankAccountNumber())
                .fromBankName(command.getFromBankName())
                .fromBankAccountNumber(command.getFromBankAccountNumber())
                .moneyAmount(command.getMoneyAmount())
                .build();

        // Command -> Event Sourcing
        commandGateway.send(createFirmbankingRequestCommand).whenComplete(
                (result, throwable) -> {
                    if (throwable != null) {
                        log.error("throwable : {}", throwable);
                        throw new RuntimeException(throwable);
                    } else {
                        log.info("createdFirmbankingRequestCommand completed, Aggregate Id : {}", result.toString());
                        //Request Firmbanking의 DB save
                        FirmbankingRequestJpaEntity requestedEntity = requestFirmbankingPort.createFirmbankingRequest(
                                new FirmbankingRequest.FromBankName(command.getFromBankName()),
                                new FirmbankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                                new FirmbankingRequest.ToBankName(command.getToBankName()),
                                new FirmbankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                                new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                                new FirmbankingRequest.FirmbankingStatus(0),
                                new FirmbankingRequest.FirmbankingAggregateIdentifier(result.toString())
                        );

                        //은행에 펌뱅킹 요청
                        FirmbankingResult firmbankingResult = requestExternalFirmbanking.requestExternalFirmbanking(
                                new ExternalFirmbankingRequest(
                                        command.getFromBankName(),
                                        command.getFromBankAccountNumber(),
                                        command.getToBankName(),
                                        command.getToBankAccountNumber(),
                                        command.getMoneyAmount()
                                ));

                        //결과에 따라서 DB save
                        if (firmbankingResult.getResultCode() == 0) {
                            // 성공
                            requestedEntity.setFirmbankingStatus(1);
                        } else {
                            // 실패
                            requestedEntity.setFirmbankingStatus(2);
                        }

                        requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity);
                    }
                }
        );
    }

    @Override
    public void updateFirmbankingByEvent(UpdateFirmbankingCommand command) {
        UpdateFirmbankingRequestCommand updateFirmbankingRequestCommand = new UpdateFirmbankingRequestCommand(
                command.getFirmbankingAggregateIdentifier(), command.getFirmbankingStatus());

        // Command
        commandGateway.send(updateFirmbankingRequestCommand).whenComplete(
                (result, throwable) -> {
                    if (throwable!= null) {
                        log.error("throwable : {}", throwable);
                        throw new RuntimeException(throwable);
                    } else {
                        log.info("updateFirmbankingRequestCommand completed, Aggregate Id : {}", result.toString());
                        //FirmbankingRequest update
                        FirmbankingRequestJpaEntity entity = requestFirmbankingPort.getFirmbankingRequest(
                                new FirmbankingAggregateIdentifier(command.getFirmbankingAggregateIdentifier()));

                        //status의 변경으로 인한 외부 은행과의 커뮤니케이션
                        // if rollback -> 0, status 변경도 해주겠지만
                        // + 기존 펌뱅킹 정보에서 from <-> to 가 변경된 펌뱅킹을 요청하는 새로운 요청

                        entity.setFirmbankingStatus(command.getFirmbankingStatus());
                        requestFirmbankingPort.modifyFirmbankingRequest(entity);
                    }
                }
        );

    }
}
