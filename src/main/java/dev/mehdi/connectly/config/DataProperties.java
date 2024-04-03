package dev.mehdi.connectly.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.data")
@Getter @Setter
public class DataProperties {
    private Boolean init;
}
