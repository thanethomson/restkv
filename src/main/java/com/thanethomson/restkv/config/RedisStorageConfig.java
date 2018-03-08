package com.thanethomson.restkv.config;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.thanethomson.restkv.storage.KeyValueStore;
import com.thanethomson.restkv.storage.RedisKeyValueStore;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Data
@Configuration
@ConfigurationProperties(prefix = "restkv.redis")
@Profile({"redis"})
public class RedisStorageConfig {

    private String url;

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean(destroyMethod = "shutdown")
    public RedisClient redisClient(ClientResources clientResources) {
        return RedisClient.create(clientResources, url);
    }

    @Bean(destroyMethod = "close")
    public StatefulRedisConnection<String, String> redisConnection(RedisClient redisClient) {
        return redisClient.connect();
    }

    @Bean
    public RedisCommands<String, String> redisCommands(StatefulRedisConnection<String, String> redisConnection) {
        return redisConnection.sync();
    }

    @Bean
    public KeyValueStore keyValueStore(RedisCommands<String, String> redisCommands, ListeningExecutorService executorService) {
        return new RedisKeyValueStore(redisCommands, executorService);
    }

}
