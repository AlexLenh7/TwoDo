package com.PityPlanner.PityPlanner.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.PityPlanner.PityPlanner.entity.User;
import com.PityPlanner.PityPlanner.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public OAuth2SuccessHandler(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        User user = userService.findOrCreateUser(email, name, picture, "google");

        String token = jwtUtil.generateToken(email, user.getId().toString());

        // Redirect to frontend with token — update this URL to match your frontend
        String redirectUrl = "http://localhost:5173/auth/callback?token=" + token;
        response.sendRedirect(redirectUrl);
    }
}