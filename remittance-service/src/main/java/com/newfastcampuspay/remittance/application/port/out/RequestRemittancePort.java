package com.newfastcampuspay.remittance.application.port.out;


import com.newfastcampuspay.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.newfastcampuspay.remittance.application.port.in.RequestRemittanceCommand;

public interface RequestRemittancePort {

	RemittanceRequestJpaEntity createRemittanceRequestHistory(RequestRemittanceCommand command);

	boolean saveRemittanceRequestHistory(RemittanceRequestJpaEntity entity);
}
