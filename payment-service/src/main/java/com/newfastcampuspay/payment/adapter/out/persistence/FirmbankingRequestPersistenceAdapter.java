package com.newfastcampuspay.payment.adapter.out.persistence;

import com.newfastcampuspay.common.PersistenceAdapter;
import com.newfastcampuspay.payment.application.port.out.CreatePaymentPort;
import com.newfastcampuspay.payment.domain.Payment;
import java.util.Date;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class FirmbankingRequestPersistenceAdapter implements CreatePaymentPort {

    private final SpringDataPaymentRepository paymentRepository;

    private final FirmbankingRequestMapper mapper;

    @Override
    public Payment createPayment(String requestMembershipId, String requestPrice, String franchiseId, String franchiseFeeRate) {

        PaymentJpaEntity jpaEntity = paymentRepository.save(
                new PaymentJpaEntity(
                        requestMembershipId,
                        Integer.parseInt(requestPrice),
                        franchiseId,
                        franchiseFeeRate,
                        0,  // 0: 승인, 1: 실패 , 2: 정산 완료
                        null
                ));

        return mapper.mapToDomainEntity(jpaEntity);
    }
}
