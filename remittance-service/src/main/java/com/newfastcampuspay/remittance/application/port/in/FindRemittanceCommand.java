package com.newfastcampuspay.remittance.application.port.in;


import com.newfastcampuspay.common.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class FindRemittanceCommand extends SelfValidating<FindRemittanceCommand> {

    @NotNull
    private String fromMembershipId; //from membership

    public FindRemittanceCommand( String fromMembershipId) {
        this.fromMembershipId = fromMembershipId;

        this.validateSelf();
    }
}
