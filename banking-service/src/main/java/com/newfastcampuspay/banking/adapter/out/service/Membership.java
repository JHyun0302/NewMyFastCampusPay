package com.newfastcampuspay.banking.adapter.out.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Membership { // for banking-service (!= Membership-service)

    private String membershipId;

    private String name;

    private String email;

    private String address;

    private boolean isValid;

    private boolean isCorp;
}