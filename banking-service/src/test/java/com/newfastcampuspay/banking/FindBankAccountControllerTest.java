package com.newfastcampuspay.banking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newfastcampuspay.banking.adapter.in.web.RegisterBankAccountRequest;
import com.newfastcampuspay.banking.adapter.out.persistence.SpringDataRegisteredBankAccountRepository;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.BankAccountNumber;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.BankName;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.LinkedStatusIsValid;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.MembershipId;
import com.newfastcampuspay.banking.domain.RegisteredBankAccount.RegisteredBankAccountId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FindBankAccountControllerTest {

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
    public void testFindBankAccount() throws Exception {
        RegisterBankAccountRequest request = new RegisterBankAccountRequest("1", "newFastcampusBank", "1234567890", true);

        RegisteredBankAccount expect = RegisteredBankAccount.generateRegisteredBankAccount(
                new RegisteredBankAccountId("1"),
                new MembershipId("1"),
                new BankName("newFastcampusBank"),
                new BankAccountNumber("1234567890"),
                new LinkedStatusIsValid(true)
        );

        mockMvc.perform(
                MockMvcRequestBuilders.post("/banking/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/banking/account/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.membershipId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankName").value("newFastcampusBank"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankAccountNumber").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.linkedStatusIsValid").value(true));
    }

}