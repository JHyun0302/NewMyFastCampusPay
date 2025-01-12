package com.newfastcampuspay.payment.application.port.in;

import com.newfastcampuspay.payment.domain.Payment;

public interface RequestPaymentUseCase {

    Payment requestPayment(RequestPaymentCommand command);
}
