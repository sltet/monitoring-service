package com.paysafe.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@RefreshScope
@Component
@ConfigurationProperties(prefix = "scheduler")
public class SchedulerProperties {

    private Long rate;
}
