package com.newfastcampuspay.banking.application.service;

import com.newfastcampuspay.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import com.newfastcampuspay.banking.adapter.out.persistence.FirmbankingRequestMapper;
import com.newfastcampuspay.banking.application.port.in.FindFirmbankingCommand;
import com.newfastcampuspay.banking.application.port.in.FindFirmbankingUseCase;
import com.newfastcampuspay.banking.application.port.out.FindFirmbankingPort;
import com.newfastcampuspay.banking.domain.FirmbankingRequest;
import com.newfastcampuspay.banking.domain.FirmbankingRequest.FirmbankingRequestId;
import com.newfastcampuspay.common.UseCase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class FindFirmbankingService implements FindFirmbankingUseCase {

    private final FindFirmbankingPort findFirmbankingPort;

    private final FirmbankingRequestMapper firmBankingRequestMapper;

    @Override
    public FirmbankingRequest findFirmbankingRequest(FindFirmbankingCommand command) {
        FirmbankingRequestJpaEntity jpaEntity = findFirmbankingPort.findFirmbanking(
                new FirmbankingRequestId(command.getMembershipId()));

        return firmBankingRequestMapper.mapToDomainEntity(jpaEntity, UUID.fromString(jpaEntity.getUuid()));
    }
}
