package com.newfastcampuspay.money.adapter.out.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataMemberMoneyRepository extends JpaRepository<MemberMoneyJpaEntity, Long> {

    @Query("SELECT e  FROM MemberMoneyJpaEntity e WHERE e.membershipId = :membershipId")
    List<MemberMoneyJpaEntity> findByMembershipId(@Param("membershipId") Long membershipId);

    @Query("SELECT e  FROM MemberMoneyJpaEntity e WHERE e.membershipId in :membershipIds")
    List<MemberMoneyJpaEntity> findMemberMoneyListByMembershipIds(@Param("membershipIds") List<Long> membershipIds);
}
