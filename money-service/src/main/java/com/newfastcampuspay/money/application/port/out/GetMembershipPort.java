package com.newfastcampuspay.money.application.port.out;

public interface GetMembershipPort {
    MembershipStatus getMembership(String membershipId);
}
