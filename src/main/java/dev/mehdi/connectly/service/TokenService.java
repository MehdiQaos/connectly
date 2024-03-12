package dev.mehdi.connectly.service;



import dev.mehdi.connectly.model.Member;
import dev.mehdi.connectly.model.Token;

import java.util.Optional;

public interface TokenService {
    Token save(Token token);
    void revokeAllTokens(Member member);
    Optional<Token> getByToken(String token);
}
