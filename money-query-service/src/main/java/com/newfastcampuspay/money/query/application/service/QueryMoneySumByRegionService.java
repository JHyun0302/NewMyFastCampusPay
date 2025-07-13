package com.newfastcampuspay.money.query.application.service;

import com.newfastcampuspay.common.UseCase;
import com.newfastcampuspay.money.query.adapter.axon.QueryMoneySumByAddress;
import com.newfastcampuspay.money.query.application.port.in.QueryMoneySumByRegionQuery;
import com.newfastcampuspay.money.query.application.port.in.QueryMoneySumByRegionUseCase;
import com.newfastcampuspay.money.query.domain.MoneySumByRegion;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class QueryMoneySumByRegionService implements QueryMoneySumByRegionUseCase {
    private final QueryGateway queryGateway;


    @Override
    public MoneySumByRegion queryMoneySumByRegion(QueryMoneySumByRegionQuery query) {
        try {
            return queryGateway.query(new QueryMoneySumByAddress(query.getAddress())
                    , MoneySumByRegion.class).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
