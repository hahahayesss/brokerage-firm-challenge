package io.atk.brokeragefirmchallenge.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("security")
public class SecurityProperties {
    private String prefix;
    private String secretKey;
    private long validity;
}
