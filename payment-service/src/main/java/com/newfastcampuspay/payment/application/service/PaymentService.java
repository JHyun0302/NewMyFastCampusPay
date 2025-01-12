package com.newfastcampuspay.payment.application.service;

import com.newfastcampuspay.common.UseCase;
import com.newfastcampuspay.payment.application.port.in.RequestPaymentCommand;
import com.newfastcampuspay.payment.application.port.in.RequestPaymentUseCase;
import com.newfastcampuspay.payment.application.port.out.CreatePaymentPort;
import com.newfastcampuspay.payment.application.port.out.GetMembershipPort;
import com.newfastcampuspay.payment.application.port.out.GetRegisteredBankAccountPort;
import com.newfastcampuspay.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class PaymentService implements RequestPaymentUseCase {

    private final CreatePaymentPort createPaymentPort;

//    private final GetMembershipPort getMembershipPort;

//    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;

    //TODO: Money Service -> Member Money 정보를 가져오기 위한 Port

    @Override
    public Payment requestPayment(RequestPaymentCommand command) {

        /**
         * 충전, 멤버십 확인, 머니 유효성 확인... 등등 수행해야 함!
         */
//        getMembershipPort.getMembership(command.getRequestMembershipId());

//        getRegisteredBankAccountPort.getRegisteredBankAccount(command.getRequestMembershipId());
        //TODO: 검증 로직 추가

        //createPaymentPort
        return createPaymentPort.createPayment(
                command.getRequestMembershipId(),
                command.getRequestPrice(),
                command.getFranchiseId(),
                command.getFranchiseFeeRate());
    }
}
