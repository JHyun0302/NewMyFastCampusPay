package com.newfastcampuspay.remittance.application.service;


import com.newfastcampuspay.common.UseCase;
import com.newfastcampuspay.remittance.adapter.out.persistence.RemittanceRequestMapper;
import com.newfastcampuspay.remittance.application.port.in.FindRemittanceCommand;
import com.newfastcampuspay.remittance.application.port.in.FindRemittanceUseCase;
import com.newfastcampuspay.remittance.application.port.out.FindRemittancePort;
import com.newfastcampuspay.remittance.domain.RemittanceRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindRemittanceService implements FindRemittanceUseCase {

    private final FindRemittancePort findRemittancePort;

    private final RemittanceRequestMapper mapper;


    @Override
    public List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command) {

        findRemittancePort.findRemittanceHistory(command);
        return null;
    }
}




