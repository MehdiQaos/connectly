package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Token;
import dev.mehdi.connectly.repository.TokenRepository;
import dev.mehdi.connectly.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(
            TokenRepository tokenRepository
    ) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token save(Token token) {
        return tokenRepository.save(token);
    }

    public Optional<Token> getByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void revokeAllTokens(Member member) {
        tokenRepository.revokeAllByUser(member);
    }
}
