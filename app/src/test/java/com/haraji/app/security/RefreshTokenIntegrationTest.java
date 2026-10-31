package com.haraji.app.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haraji.security.constant.RoleType;
import com.haraji.security.database.entity.RefreshTokenEntity;
import com.haraji.security.database.entity.UserEntity;
import com.haraji.security.database.repository.RefreshTokenRepository;
import com.haraji.security.database.repository.UserRepository;
import com.haraji.security.service.token.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RefreshTokenIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Test
    void shouldRotateRefreshTokenAndLogoutSuccessfully() throws Exception {

        // Arrange
        UserEntity user = createTestUser();
        String initialRefreshToken = refreshTokenService.createRefreshToken(user);

        // Act 1: Refresh initial token
        String firstResponse = mockMvc.perform(post("/auth/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("refreshToken", initialRefreshToken)
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode firstResult = objectMapper.readTree(firstResponse);
        String firstAccessToken = firstResult.get("accessToken").asText();
        String firstRefreshToken = firstResult.get("refreshToken").asText();

        assertNotNull(firstAccessToken);
        assertNotEquals(initialRefreshToken, firstRefreshToken);

        // Assert 1: Initial token must be revoked
        assertTrue(getRefreshToken(initialRefreshToken).getRevoked());
        assertFalse(getRefreshToken(firstRefreshToken).getRevoked());

        // Act 2: Reusing initial token must fail
        mockMvc.perform(post("/auth/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("refreshToken", initialRefreshToken)
                        )))
                .andExpect(status().is4xxClientError());

        // Act 3: New refresh token must work
        String secondResponse = mockMvc.perform(post("/auth/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("refreshToken", firstRefreshToken)
                        )))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode secondResult = objectMapper.readTree(secondResponse);
        String secondRefreshToken = secondResult.get("refreshToken").asText();

        assertNotEquals(firstRefreshToken, secondRefreshToken);
        assertTrue(getRefreshToken(firstRefreshToken).getRevoked());
        assertFalse(getRefreshToken(secondRefreshToken).getRevoked());

        // Act 4: Logout
        mockMvc.perform(post("/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("refreshToken", secondRefreshToken)
                        )))
                .andExpect(status().isNoContent());

        // Assert 4: Logout must revoke current refresh token
        assertTrue(getRefreshToken(secondRefreshToken).getRevoked());

        // Act 5: Refresh after logout must fail
        mockMvc.perform(post("/auth/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("refreshToken", secondRefreshToken)
                        )))
                .andExpect(status().is4xxClientError());
    }

    private UserEntity createTestUser() {
        UserEntity user = new UserEntity();
        user.setPasswordHash("{noop}TestPassword123");
        user.setRole(RoleType.VIEWER);
        user.setEnabled(true);
        user.setLocked(false);

        return userRepository.saveAndFlush(user);
    }

    private RefreshTokenEntity getRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new AssertionError("Refresh token not found"));
    }
}
