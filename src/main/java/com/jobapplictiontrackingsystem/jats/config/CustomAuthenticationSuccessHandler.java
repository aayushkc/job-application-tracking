package com.jobapplictiontrackingsystem.jats.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_RECRUITER")) {
            response.sendRedirect("/recruiter");
        } else if (roles.contains("ROLE_JOB_SEEKER")) {
            response.sendRedirect("/seeker/jobs");
        } else {
            response.sendRedirect("/");  // Default redirect
        }
    }
}
