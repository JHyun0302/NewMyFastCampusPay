package com.newfastcampuspay.banking.adapter.out.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataFirmbankingRequestRepository extends JpaRepository<FirmbankingRequestJpaEntity, Long> {

    FirmbankingRequestJpaEntity findByRequestFirmbankingId(Long requestFirmbankingId);

    @Query("SELECT e FROM FirmbankingRequestJpaEntity e WHERE e.aggregateIdentifier = :aggregateIdentifier")
    List<FirmbankingRequestJpaEntity> findByAggregateIdentifier(@Param("aggregateIdentifier") String aggregateIdentifier);
}
