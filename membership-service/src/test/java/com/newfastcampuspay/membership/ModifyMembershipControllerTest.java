package com.newfastcampuspay.membership;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newfastcampuspay.membership.adapter.in.web.ModifyMembershipRequest;
import com.newfastcampuspay.membership.adapter.in.web.RegisterMembershipRequest;
import com.newfastcampuspay.membership.domain.Membership;
import com.newfastcampuspay.membership.domain.Membership.MembershipAddress;
import com.newfastcampuspay.membership.domain.Membership.MembershipEmail;
import com.newfastcampuspay.membership.domain.Membership.MembershipId;
import com.newfastcampuspay.membership.domain.Membership.MembershipIsCorp;
import com.newfastcampuspay.membership.domain.Membership.MembershipIsValid;
import com.newfastcampuspay.membership.domain.Membership.MembershipName;
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
class ModifyMembershipControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;


	@Test
	@Transactional
	public void testModifyMembership() throws Exception {
		RegisterMembershipRequest request = new RegisterMembershipRequest("name", "email", "address", false);

		Membership expect = Membership.generateMember(
				new MembershipId("1"),
				new MembershipName("name"),
				new MembershipEmail("email"),
				new MembershipAddress("address"),
				new MembershipIsValid(true),
				new MembershipIsCorp(false)
		);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/membership/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(request))
		);

		// 잠시 대기
		Thread.sleep(1000);

		ModifyMembershipRequest modifyMembershipRequest = new ModifyMembershipRequest("1", "modifyName", "modifyEmail", "modifyAddress", true, true);

		mockMvc.perform(
						MockMvcRequestBuilders.post("/membership/modify")
								.contentType(MediaType.APPLICATION_JSON)
								.content(mapper.writeValueAsString(modifyMembershipRequest))
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(modifyMembershipRequest)));
	}
}