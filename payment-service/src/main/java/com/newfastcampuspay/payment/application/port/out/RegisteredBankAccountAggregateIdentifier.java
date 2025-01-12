package com.newfastcampuspay.payment.application.port.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredBankAccountAggregateIdentifier {

    private String registeredBankAccountId;

    private String aggregateIdentifier;

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;

    @Override
    public String toString() {
        return "RegisteredBankAccountAggregateIdentifier{" +
                "aggregateIdentifier='" + aggregateIdentifier + '\'' +
                ", registeredBankAccountId='" + registeredBankAccountId + '\'' +
                ", membershipId='" + membershipId + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                '}';
    }
}
