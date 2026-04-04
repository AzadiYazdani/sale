package com.haraji.app.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haraji.app.model.dto.UploadedMultipartFileDto;
import com.haraji.security.api.dto.login.LoginRequestDto;
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
        assertEquals(200, result.getStatus());
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

    @Test
    void upload_Correct() throws Exception {
        MockHttpServletResponse response = getToken();
        String token = response.getContentAsString();

        UploadedMultipartFileDto request = uploadRequest();

        MvcResult requestResult =
                mockMvc.perform(post("/file/upload")
                        .content(asJsonString(request)
                        )
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                        .andExpect(status().isOk())
                        .andReturn();

        String result = requestResult.getResponse().getContentAsString();
        System.out.println(result);
        assertEquals(requestResult.getResponse().getStatus(), 200);
    }

    private LoginRequestDto getAuthRequest() {
        return LoginRequestDto.builder().username("A.Yazdani").password("TIdaza123").build();
    }

    private UploadedMultipartFileDto uploadRequest() {

        return new UploadedMultipartFileDto(
                "Hello, World!".getBytes(),
                MediaType.TEXT_PLAIN_VALUE,
                "file",
                "hello.txt",
                null
        );
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
