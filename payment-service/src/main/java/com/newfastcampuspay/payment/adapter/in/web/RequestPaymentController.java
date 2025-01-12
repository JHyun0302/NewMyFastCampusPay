package com.newfastcampuspay.payment.adapter.in.web;

import com.newfastcampuspay.common.WebAdapter;
import com.newfastcampuspay.payment.application.port.in.RequestPaymentCommand;
import com.newfastcampuspay.payment.application.port.in.RequestPaymentUseCase;
import com.newfastcampuspay.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestPaymentController {

    private final RequestPaymentUseCase requestPaymentUseCase;

    @PostMapping("/payment/request")
    Payment requestPayment(PaymentRequest request) {
        return requestPaymentUseCase.requestPayment(
                new RequestPaymentCommand(
                    request.getRequestMembershipId(),
                        request.getRequestPrice(),
                        request.getFranchiseId(),
                        request.getFranchiseFeeRate()
                )
        );
    }
}
