package dev.mehdi.connectly.service;

import dev.mehdi.connectly.dto.auth.AuthRequest;
import dev.mehdi.connectly.dto.auth.AuthResponse;
import dev.mehdi.connectly.dto.member.MemberRequestDto;
import dev.mehdi.connectly.model.Member;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface AuthService {
    AuthResponse authenticate(AuthRequest authRequest);

    AuthResponse register(MemberRequestDto memberRequest);

    Member hashPasswordAndSave(MemberRequestDto memberRequest);

    AuthResponse refresh(HttpServletRequest request) throws IOException;
}
