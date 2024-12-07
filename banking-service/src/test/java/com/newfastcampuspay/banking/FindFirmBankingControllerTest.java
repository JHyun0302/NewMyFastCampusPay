package com.newfastcampuspay.banking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newfastcampuspay.banking.adapter.in.web.RequestFirmbankingRequest;
import com.newfastcampuspay.banking.adapter.out.persistence.SpringDataRegisteredBankAccountRepository;
import com.newfastcampuspay.banking.application.port.in.RequestFirmbankingCommand;
import com.newfastcampuspay.banking.application.service.RequestFirmbankingService;
import com.newfastcampuspay.banking.domain.FirmbankingRequest;
import com.newfastcampuspay.banking.domain.FirmbankingRequest.FirmbankingRequestId;
import com.newfastcampuspay.banking.domain.FirmbankingRequest.FirmbankingStatus;
import com.newfastcampuspay.banking.domain.FirmbankingRequest.FromBankAccountNumber;
import com.newfastcampuspay.banking.domain.FirmbankingRequest.FromBankName;
import com.newfastcampuspay.banking.domain.FirmbankingRequest.MoneyAmount;
import com.newfastcampuspay.banking.domain.FirmbankingRequest.ToBankAccountNumber;
import com.newfastcampuspay.banking.domain.FirmbankingRequest.ToBankName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
class FindFirmBankingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RequestFirmbankingService requestFirmbankingService;

    @Autowired
    private SpringDataRegisteredBankAccountRepository bankAccountRepository;

    @BeforeEach
    void setUp() {
        bankAccountRepository.deleteAll();
    }

    @Test
    void testFindFirmBanking() throws Exception {
        RequestFirmbankingRequest request = new RequestFirmbankingRequest("frombank", "1234567890", "tobank",
                "0987654321", 10000);

        FirmbankingRequest firmbankingRequest = requestFirmbankingService.requestFirmbanking(
                new RequestFirmbankingCommand(request.getFromBankName(), request.getFromBankAccountNumber(),
                        request.getToBankName(), request.getToBankAccountNumber(), request.getMoneyAmount())
        );

        FirmbankingRequest expect = FirmbankingRequest.generateFirmbankingRequest(
                new FirmbankingRequestId("1"),
                new FromBankName("frombank"),
                new FromBankAccountNumber("1234567890"),
                new ToBankName("tobank"),
                new ToBankAccountNumber("0987654321"),
                new MoneyAmount(10000),
                new FirmbankingStatus(1),
                firmbankingRequest.getUuid()
        );

        mockMvc.perform(
                MockMvcRequestBuilders.post("/banking/firmbanking/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        );

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/banking/firmbanking/request/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(expect)));
    }
}
