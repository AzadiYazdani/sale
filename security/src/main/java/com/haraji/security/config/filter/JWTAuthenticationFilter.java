package com.haraji.security.config.filter;

import com.haraji.security.service.CustomUserDetailsService;
import com.haraji.security.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    // هدر جدید برای ارسال توکن تمدید شده
    private static final String REFRESHED_TOKEN_HEADER = "X-Refreshed-Token";

    public JWTAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;

        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = jwtUtil.parseJwtToken(request);
            if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
                String username = jwtUtil.extractUsername(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // بررسی و تمدید توکن در صورت نیاز
                if (jwtUtil.shouldRefreshToken(jwt)) {
                    String refreshedToken = jwtUtil.refreshToken(jwt);
                    response.setHeader(REFRESHED_TOKEN_HEADER, refreshedToken);
                    logger.debug("JWT token refreshed for user: " + username);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }


}
