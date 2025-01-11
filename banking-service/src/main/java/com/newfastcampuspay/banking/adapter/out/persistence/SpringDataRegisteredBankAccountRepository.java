package com.newfastcampuspay.banking.adapter.out.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataRegisteredBankAccountRepository extends JpaRepository<RegisteredBankAccountJpaEntity, Long> {

    RegisteredBankAccountJpaEntity findByRegisteredBankAccountId(Long registeredBankAccountId);

    @Query("SELECT e FROM RegisteredBankAccountJpaEntity e WHERE e.membershipId = :membershipId")
    List<RegisteredBankAccountJpaEntity> findByMembershipId(@Param("membershipId") String membershipId);
}
