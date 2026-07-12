package com.haraji.security.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haraji.security.api.dto.login.LoginPasswordRequest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void login_Correct() throws Exception {
        MockHttpServletResponse result = getToken();
        String response = result.getContentAsString();
        System.out.println(response);
        assertEquals(result.getStatus(), 200);
    }

    private MockHttpServletResponse getToken() throws Exception {
        MvcResult requestResult =
                mockMvc.perform(post("/authentication/login")
                        .content(asJsonString(getAuthRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                        .andExpect(status().isOk())
                        .andReturn();
        return requestResult.getResponse();
    }

    private LoginPasswordRequest getAuthRequest() {
//        return LoginPasswordRequest.builder().username("A.Yazdani").password("TIdaza123").build();
        return null;
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
