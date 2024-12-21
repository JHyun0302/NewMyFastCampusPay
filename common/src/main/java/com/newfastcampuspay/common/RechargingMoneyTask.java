package com.newfastcampuspay.common;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RechargingMoneyTask { // Increase Money

    private String taskID;

    private String taskName;

    private String membershipId;

    private List<SubTask> subTaskList;

    //법인계좌
    private String toBankName;

    //법인계좌 번호
    private String toBankAccountNumber;

    private int moneyAmount; // only won
}
