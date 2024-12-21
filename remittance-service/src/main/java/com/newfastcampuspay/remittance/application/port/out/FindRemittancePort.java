package com.newfastcampuspay.remittance.application.port.out;


import com.newfastcampuspay.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.newfastcampuspay.remittance.application.port.in.FindRemittanceCommand;
import com.newfastcampuspay.remittance.application.port.in.RequestRemittanceCommand;
import java.util.List;

public interface FindRemittancePort {

	List<RemittanceRequestJpaEntity> findRemittanceHistory(FindRemittanceCommand command);

}
