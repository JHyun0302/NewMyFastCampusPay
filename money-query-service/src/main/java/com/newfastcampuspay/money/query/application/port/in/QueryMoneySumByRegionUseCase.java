package com.newfastcampuspay.money.query.application.port.in;

import com.google.type.Money;
import com.newfastcampuspay.money.query.domain.MoneySumByRegion;

public interface QueryMoneySumByRegionUseCase {

    MoneySumByRegion queryMoneySumByRegion(QueryMoneySumByRegionQuery query);
}
