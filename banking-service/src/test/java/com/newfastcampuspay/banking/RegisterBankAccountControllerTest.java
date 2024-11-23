package com.newfastcampuspay.banking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newfastcampuspay.banking.adapter.in.web.RegisterBankAccountRequest;
import com.newfastcampuspay.banking.adapter.out.persistence.SpringDataRegisteredBankAccountRepository;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RegisterBankAccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private SpringDataRegisteredBankAccountRepository bankAccountRepository;

	@BeforeEach
	void setUp() {
		bankAccountRepository.deleteAll();
	}


	@Test
	public void testRegisterMembership() throws Exception {
		RegisterBankAccountRequest request = new RegisterBankAccountRequest("1", "fastcampusBank", "1234567890", true);

		RegisteredBankAccount expect = RegisteredBankAccount.generateRegisteredBankAccount(
				new RegisteredBankAccount.RegisteredBankAccountId("1"),
				new RegisteredBankAccount.MembershipId("1"),
				new RegisteredBankAccount.BankName("fastcampusBank"),
				new RegisteredBankAccount.BankAccountNumber("1234567890"),
				new RegisteredBankAccount.LinkedStatusIsValid(true)
		);

		mockMvc.perform(
						MockMvcRequestBuilders.post("/banking/account/register")
								.contentType(MediaType.APPLICATION_JSON)
								.content(mapper.writeValueAsString(request))
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.membershipId").value("1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.bankName").value("fastcampusBank"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.bankAccountNumber").value("1234567890"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.linkedStatusIsValid").value(true));
	}
}
