package com.migtation.server.configs.seccurity.jwt;

import com.migtation.server.configs.seccurity.web.UserDetailsCustom;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilterChains extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsCustom userDetailsCustom;

    @Autowired
    public JwtFilterChains(JwtProvider jwtProvider, UserDetailsCustom userDetailsCustom) {
        this.jwtProvider = jwtProvider;
        this.userDetailsCustom = userDetailsCustom;
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("bearerToken: " + bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    private String getTokenFromCookieOrigin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            logger.debug(cookie.toString() + " from Class " + this.getClass().getSimpleName());
            if ("access_token".equals(cookie.getName()))
                return cookie.getValue();
        }
        return null;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String jwtToken = getTokenFromCookieOrigin(request);

        if (jwtToken != null && jwtProvider.validateToken(jwtToken)) {
            System.out.println("jwtToken: " + jwtToken);
            String username = jwtProvider.getUsernameFromJWT(jwtToken);

            UserDetails userDetails = userDetailsCustom.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            System.out.println("jwtToken: " + jwtToken);
            System.out.println("Token was removed in the cookie or expired!");
        }
        logger.debug("doFilterInternal filter >>>");

        filterChain.doFilter(request, response);
    }
}
