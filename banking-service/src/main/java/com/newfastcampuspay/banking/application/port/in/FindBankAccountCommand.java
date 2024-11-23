package com.newfastcampuspay.banking.application.port.in;

import com.newfastcampuspay.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class FindBankAccountCommand extends SelfValidating<FindBankAccountCommand> {

    @NotNull
    private final String membershipId;

}
