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

    private String host;
    private int port;
    private int database;
    private String password;
    private String namespace;
    private Duration timeout;
}
