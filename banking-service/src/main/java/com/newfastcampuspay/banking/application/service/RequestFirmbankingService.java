package com.newfastcampuspay.banking.application.service;

import com.newfastcampuspay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.newfastcampuspay.banking.adapter.out.external.bank.FirmbankingResult;
import com.newfastcampuspay.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import com.newfastcampuspay.banking.adapter.out.persistence.FirmbankingRequestMapper;
import com.newfastcampuspay.banking.adapter.out.persistence.RegisterBankAccountMapper;
import com.newfastcampuspay.banking.application.port.in.RequestFirmbankingCommand;
import com.newfastcampuspay.banking.application.port.in.RequestFirmbankingUseCase;
import com.newfastcampuspay.banking.application.port.out.RequestExternalFirmbanking;
import com.newfastcampuspay.banking.application.port.out.RequestFirmbankingPort;
import com.newfastcampuspay.banking.domain.FirmbankingRequest;
import com.newfastcampuspay.common.UseCase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase {

    private final FirmbankingRequestMapper mapper;

    private final RequestFirmbankingPort requestFirmbankingPort;

    private final RequestExternalFirmbanking requestExternalFirmbanking;

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
                new FirmbankingRequest.FirmbankingStatus(0)
        );

        // 2. 외부 은행에 펌뱅킹 요청
        FirmbankingResult result = requestExternalFirmbanking.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber()
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
}
