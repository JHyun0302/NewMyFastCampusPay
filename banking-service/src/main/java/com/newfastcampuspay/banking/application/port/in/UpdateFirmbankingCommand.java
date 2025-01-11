package com.newfastcampuspay.banking.application.port.in;

import com.newfastcampuspay.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateFirmbankingCommand extends SelfValidating<UpdateFirmbankingCommand> {

    @NotNull
    private final String firmbankingAggregateIdentifier;

    @NotNull
    private final int firmbankingStatus;


    public UpdateFirmbankingCommand(String firmbankingAggregateIdentifier, int firmbankingStatus) {
        this.firmbankingAggregateIdentifier = firmbankingAggregateIdentifier;
        this.firmbankingStatus = firmbankingStatus;

        this.validateSelf();
    }
}
