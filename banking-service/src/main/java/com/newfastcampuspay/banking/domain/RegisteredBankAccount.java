package com.newfastcampuspay.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisteredBankAccount {

    @Getter
    private final String registeredBankAccountId;

    @Getter
    private final String membershipId;

    @Getter
    private final String bankName; //enum으로 대체하는 것이 좋음

    @Getter
    private final String bankAccountNumber;

    @Getter
    private final boolean linkedStatusIsValid;

    public static RegisteredBankAccount generateRegisteredBankAccount(
            RegisteredBankAccount.RegisteredBankAccountId registeredBankAccountId,
            RegisteredBankAccount.MembershipId membershipId,
            RegisteredBankAccount.BankName bankName,
            RegisteredBankAccount.BankAccountNumber bankAccountNumber,
            RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid

    ) {

        return new RegisteredBankAccount(
                registeredBankAccountId.registeredBankAcountId,
                membershipId.mebershipId,
                bankName.bankName,
                bankAccountNumber.bankAccountNumber,
                linkedStatusIsValid.linkedStatusIsValid
        );
    }

    @Value
    public static class RegisteredBankAccountId {
        public RegisteredBankAccountId(String value) {
            this.registeredBankAcountId = value;
        }
        String registeredBankAcountId;
    }

    @Value
    public static class MembershipId {
        public MembershipId(String value) {
            this.mebershipId = value;
        }
        String mebershipId;
    }

    @Value
    public static class BankName {
        public BankName(String value) {
            this.bankName = value;
        }
        String bankName;
    }

    @Value
    public static class BankAccountNumber {
        public BankAccountNumber(String value) {
            this.bankAccountNumber = value;
        }
        String bankAccountNumber;
    }

    @Value
    public static class LinkedStatusIsValid {
        public LinkedStatusIsValid(boolean value) {
            this.linkedStatusIsValid = value;
        }
        boolean linkedStatusIsValid;
    }
}
