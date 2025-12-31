package com.elmergram.security;

import com.elmergram.security.jwt.JwtUtils;
import com.elmergram.security.user.AppUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final JwtUtils jwtUtils;
    private final HttpServletRequest request;

    /**
     * Return the current authenticated user's id.
     * - First tries SecurityContext principal (AppUserDetails).
     * - If no id is available, falls back to parsing the JWT from the Authorization header.
     * Returns null if no authenticated user id can be determined.
     */

    public Integer getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof AppUserDetails) {
            return ((AppUserDetails) auth.getPrincipal()).getId();
        }

        // Fallback: try to parse userId from token in Authorization header
        String token = jwtUtils.getJwtFromHeader(request);
        if (token != null) {
            return jwtUtils.getUserIdFromJwtToken(token);
        }

        return null;
    }
}