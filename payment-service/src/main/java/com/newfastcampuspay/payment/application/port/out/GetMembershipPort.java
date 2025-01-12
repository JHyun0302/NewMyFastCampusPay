package com.newfastcampuspay.payment.application.port.out;

public interface GetMembershipPort {

    MembershipStatus getMembership(String membershipId);
}
