package com.newfastcampuspay.remittance.application.port.in;

import com.newfastcampuspay.remittance.domain.RemittanceRequest;

public interface RequestRemittanceUseCase {

	RemittanceRequest requestRemittance(RequestRemittanceCommand command);
}
