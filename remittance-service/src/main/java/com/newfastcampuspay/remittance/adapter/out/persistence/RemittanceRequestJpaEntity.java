package com.newfastcampuspay.remittance.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "request_remittance")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemittanceRequestJpaEntity {

	@Id
	@GeneratedValue
	private Long remittanceMoneyId;

	private String fromMembershipId; //from membership

	private String toMembershipId; //to membership

	private String toBankName;

	private String toBankAccountNumber;

	private int remittanceType; //0: membership(내부 고객), 1: bank (외부 은행 계좌)

	private int amount; //송금요청 금액

	private String remittanceStatus;
}
