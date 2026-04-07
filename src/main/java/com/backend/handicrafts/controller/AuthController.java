package com.backend.handicrafts.controller;

import com.backend.handicrafts.dto.ApiResponse;
import com.backend.handicrafts.dto.GoogleLoginRequest;
import com.backend.handicrafts.dto.LoginRequest;
import com.backend.handicrafts.dto.RegisterRequest;
import com.backend.handicrafts.dto.SetRoleRequest;
import com.backend.handicrafts.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Map<String, Object>>> register(@Valid @RequestBody RegisterRequest request) {
        Map<String, Object> data = authService.register(request);
        log.info("User registered successfully. Email stored in database: {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("User registered successfully", data));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@Valid @RequestBody LoginRequest request) {
        Map<String, Object> data = authService.login(request);
        return ResponseEntity.ok(new ApiResponse<>("Login successful", data));
    }

    @PostMapping("/google")
    public ResponseEntity<ApiResponse<Map<String, Object>>> googleLogin(@Valid @RequestBody GoogleLoginRequest request) {
        Map<String, Object> data = authService.googleLogin(request.getIdToken());
        return ResponseEntity.ok(new ApiResponse<>("Google login successful", data));
    }

    @PostMapping("/set-role")
    public ResponseEntity<ApiResponse<Map<String, Object>>> setRole(@Valid @RequestBody SetRoleRequest request,
                                                                    Authentication authentication) {
        Map<String, Object> data = authService.setRole(authentication, request);
        return ResponseEntity.ok(new ApiResponse<>("Role updated successfully", data));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            HttpServletRequest request) {
        authService.logout(authorizationHeader);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(new ApiResponse<>("Logout successful", null));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Map<String, Object>>> me(Authentication authentication) {
        Map<String, Object> data = authService.getCurrentUser(authentication);
        return ResponseEntity.ok(new ApiResponse<>("Current user retrieved successfully", data));
    }
}
