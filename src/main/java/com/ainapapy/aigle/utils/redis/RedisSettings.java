package com.ainapapy.aigle.utils.redis;

import java.time.Duration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "aigle.redis")
@Getter
@Setter
public class RedisSettings {

    private String namespace;
    private Duration timeout;
    private String tokenPrefix;
    private String cachePrefix;
}
