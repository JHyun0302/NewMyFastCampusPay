package com.newfastcampuspay.payment.adapter.out.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataPaymentRepository extends JpaRepository<PaymentJpaEntity, Long> {

}