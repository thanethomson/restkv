package com.thanethomson.restkv.config;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.thanethomson.restkv.storage.InMemoryKeyValueStore;
import com.thanethomson.restkv.storage.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"inmemory"})
public class InMemoryStorageConfig {

    @Bean
    public KeyValueStore keyValueStore(ListeningExecutorService executorService) {
        return new InMemoryKeyValueStore(executorService);
    }

}
