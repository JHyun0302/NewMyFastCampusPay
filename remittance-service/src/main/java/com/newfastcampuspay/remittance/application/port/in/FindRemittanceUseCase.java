package com.newfastcampuspay.remittance.application.port.in;

import com.newfastcampuspay.remittance.domain.RemittanceRequest;
import java.util.List;

public interface FindRemittanceUseCase {

	List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command);
}
