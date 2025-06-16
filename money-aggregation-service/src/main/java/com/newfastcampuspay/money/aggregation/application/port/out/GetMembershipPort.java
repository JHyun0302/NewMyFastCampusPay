package com.newfastcampuspay.money.aggregation.application.port.out;

import java.util.List;

public interface GetMembershipPort {
    List<String> getMembershipByAddress(String address);
}
