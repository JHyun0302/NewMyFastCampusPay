package com.newfastcampuspay.membership.adapter.out.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataMembershipRepository extends JpaRepository<MembershipJpaEntity, Long> {

    @Query("SELECT e FROM MembershipJpaEntity e WHERE e.address = :address")
    List<MembershipJpaEntity> findByAddress(@Param("address") String address);
}
