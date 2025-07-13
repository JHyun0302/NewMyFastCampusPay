package com.newfastcampuspay.money.query.application.port.in;

import com.newfastcampuspay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryMoneySumByRegionQuery extends SelfValidating<QueryMoneySumByRegionQuery> {
    private final String address;
}
