package com.newfastcampuspay.banking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataFirmbankingRequestRepository extends JpaRepository<FirmbankingRequestJpaEntity, Long> {

    FirmbankingRequestJpaEntity findByRequestFirmbankingId(Long requestFirmbankingId);

}
