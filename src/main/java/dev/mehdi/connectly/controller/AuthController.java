package dev.mehdi.connectly.controller;

import dev.mehdi.connectly.dto.auth.AuthRequest;
import dev.mehdi.connectly.dto.auth.AuthResponse;
import dev.mehdi.connectly.dto.member.MemberRequestDto;
import dev.mehdi.connectly.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(
            AuthService authService
    ) {
        this.authService = authService;
    }

    @PostMapping({"login", "signin"})
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        AuthResponse authResponse = authService.authenticate(authRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping({"register", "signup"})
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid MemberRequestDto memberRequest) {
        AuthResponse authResponse = authService.register(memberRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping({"refresh"})
    public ResponseEntity<AuthResponse> refresh(HttpServletRequest request) throws IOException {
        AuthResponse authResponse = authService.refresh(request);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("test")
    public String test() {
        return "mehdi qaos";
    }
}
