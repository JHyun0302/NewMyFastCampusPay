package com.newfastcampuspay.banking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataRegisteredBankAccountRepository extends JpaRepository<RegisteredBankAccountJpaEntity, Long> {

    RegisteredBankAccountJpaEntity findByRegisteredBankAccountId(Long registeredBankAccountId);
}
