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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.*;

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
                        .content(objectMapper.writeValueAsString(Map.of("refreshToken", initialRefreshToken))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode firstResult = objectMapper.readTree(firstResponse);
        String firstRefreshToken = firstResult.get("refreshToken").asText();

        // Assert 1: Initial token must be revoked
        assertNotEquals(initialRefreshToken, firstRefreshToken);
        assertTrue(getRefreshToken(initialRefreshToken).getRevoked());
        assertFalse(getRefreshToken(firstRefreshToken).getRevoked());

        // Act 2: Rotate the new refresh token
        String secondResponse = mockMvc.perform(post("/auth/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("refreshToken", firstRefreshToken))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode secondResult = objectMapper.readTree(secondResponse);
        String secondRefreshToken = secondResult.get("refreshToken").asText();

        // Assert 2: First token must be revoked after rotation
        assertNotEquals(firstRefreshToken, secondRefreshToken);
        assertTrue(getRefreshToken(firstRefreshToken).getRevoked());
        assertFalse(getRefreshToken(secondRefreshToken).getRevoked());

        // Act 3: Logout using the latest refresh token
        mockMvc.perform(post("/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("refreshToken", secondRefreshToken))))
                .andExpect(status().isNoContent());

        // Assert 3: Logout must revoke the current refresh token
        assertTrue(getRefreshToken(secondRefreshToken).getRevoked());

        // Act 4: Refresh after logout must fail
        mockMvc.perform(post("/auth/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("refreshToken", secondRefreshToken))))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void shouldRejectInvalidRefreshToken() throws Exception {
        mockMvc.perform(post("/auth/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("refreshToken", "invalid-refresh-token")
                        )))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldAllowLogoutWithInvalidRefreshToken() throws Exception {
        mockMvc.perform(post("/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("refreshToken", "invalid-refresh-token")
                        )))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldRejectExpiredRefreshToken() throws Exception {
        UserEntity user = createTestUser();
        String expiredToken = refreshTokenService.createRefreshToken(user);

        RefreshTokenEntity refreshToken = getRefreshToken(expiredToken);
        refreshToken.setExpireAt(LocalDateTime.now().minusMinutes(1));
        refreshTokenRepository.saveAndFlush(refreshToken);

        mockMvc.perform(post("/auth/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("refreshToken", expiredToken)
                        )))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void shouldKeepUsersRefreshTokensIndependent() throws Exception {
        // Arrange
        UserEntity firstUser = createTestUser();
        UserEntity secondUser = createTestUser();

        String firstRefreshToken = refreshTokenService.createRefreshToken(firstUser);
        String secondRefreshToken = refreshTokenService.createRefreshToken(secondUser);

        // Act: Refresh first user's token
        String response = mockMvc.perform(post("/auth/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("refreshToken", firstRefreshToken)
                        )))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode result = objectMapper.readTree(response);
        String newFirstRefreshToken = result.get("refreshToken").asText();

        // Assert: First user's old token must be revoked
        assertTrue(getRefreshToken(firstRefreshToken).getRevoked());

        // Assert: Second user's token must remain active
        assertFalse(getRefreshToken(secondRefreshToken).getRevoked());

        // Act: Logout first user's new token
        mockMvc.perform(post("/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("refreshToken", newFirstRefreshToken)
                        )))
                .andExpect(status().isNoContent());

        // Assert: Logout must not affect second user's token
        assertTrue(getRefreshToken(newFirstRefreshToken).getRevoked());
        assertFalse(getRefreshToken(secondRefreshToken).getRevoked());

        // Act: Second user's token must still work
        mockMvc.perform(post("/auth/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("refreshToken", secondRefreshToken)
                        )))
                .andExpect(status().isOk());
    }


    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldAllowOnlyOneConcurrentRefreshRequest() throws Exception {
        // Arrange
        UserEntity user = createTestUser();
        String refreshToken = refreshTokenService.createRefreshToken(user);

        CountDownLatch ready = new CountDownLatch(2);
        CountDownLatch start = new CountDownLatch(1);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            Callable<Integer> refreshRequest = () -> {
                ready.countDown();
                start.await();

                return mockMvc.perform(post("/auth/token/refresh")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        Map.of("refreshToken", refreshToken)
                                )))
                        .andReturn()
                        .getResponse()
                        .getStatus();
            };

            Future<Integer> firstRequest = executor.submit(refreshRequest);
            Future<Integer> secondRequest = executor.submit(refreshRequest);

            assertTrue(ready.await(5, TimeUnit.SECONDS));

            // Act: Start both requests simultaneously
            start.countDown();

            int firstStatus = firstRequest.get(15, TimeUnit.SECONDS);
            int secondStatus = secondRequest.get(15, TimeUnit.SECONDS);

            // Assert: Exactly one request must succeed
            int successCount = (firstStatus == 200 ? 1 : 0)
                    + (secondStatus == 200 ? 1 : 0);

            int clientErrorCount = (firstStatus >= 400 && firstStatus < 500 ? 1 : 0)
                    + (secondStatus >= 400 && secondStatus < 500 ? 1 : 0);

            assertEquals(1, successCount);
            assertEquals(1, clientErrorCount);

            // Original token must be revoked
            assertTrue(getRefreshToken(refreshToken).getRevoked());

        } finally {
            start.countDown();
            executor.shutdownNow();
        }
    }


    @Test
    void shouldRevokeAllUserTokensWhenRevokedTokenIsReused() throws Exception {
        // Arrange
        UserEntity firstUser = createTestUser();
        UserEntity secondUser = createTestUser();

        String initialToken = refreshTokenService.createRefreshToken(firstUser);
        String anotherSessionToken = refreshTokenService.createRefreshToken(firstUser);
        String secondUserToken = refreshTokenService.createRefreshToken(secondUser);

        // Act 1: Rotate first user's initial token
        String response = mockMvc.perform(post("/auth/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("refreshToken", initialToken)
                        )))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode result = objectMapper.readTree(response);
        String newToken = result.get("refreshToken").asText();

        // Assert 1: New token must be active
        assertTrue(getRefreshToken(initialToken).getRevoked());
        assertFalse(getRefreshToken(newToken).getRevoked());
        assertFalse(getRefreshToken(anotherSessionToken).getRevoked());

        // Act 2: Reuse revoked token
        mockMvc.perform(post("/auth/token/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("refreshToken", initialToken)
                        )))
                .andExpect(status().is4xxClientError());

        // Assert 2: All first user's tokens must be revoked
        assertTrue(getRefreshToken(initialToken).getRevoked());
        assertTrue(getRefreshToken(newToken).getRevoked());
        assertTrue(getRefreshToken(anotherSessionToken).getRevoked());

        // Assert 3: Second user's token must remain active
        assertFalse(getRefreshToken(secondUserToken).getRevoked());
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
