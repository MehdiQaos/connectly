package dev.mehdi.connectly.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.security.jwt")
@Getter @Setter
public class JwtProperties {
    private String secretKey;
    private Long expiration;
    private Long refreshExpiration;
}
