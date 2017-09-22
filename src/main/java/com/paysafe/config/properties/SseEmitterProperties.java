package com.paysafe.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "emitter")
public class SseEmitterProperties {

    private Long timeout;
}
