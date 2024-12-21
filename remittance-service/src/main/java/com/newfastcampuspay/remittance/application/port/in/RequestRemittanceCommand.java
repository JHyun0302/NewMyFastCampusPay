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
public class RequestRemittanceCommand extends SelfValidating<RequestRemittanceCommand> {

    @NotNull
    private String fromMembershipId; //from membership

    private String toMembershipId; //to membership

    private String toBankName;

    private String toBankAccountNumber;

    private int remittanceType;

    @NotNull
    @NotBlank
    private int amount; //송금요청 금액

    public RequestRemittanceCommand(String fromMembershipId, String toMembershipId, String toBankName,
                                    String toBankAccountNumber, int remittanceType, int amount) {
        this.fromMembershipId = fromMembershipId;
        this.toMembershipId = toMembershipId;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.remittanceType = remittanceType;
        this.amount = amount;

        this.validateSelf();
    }
}
