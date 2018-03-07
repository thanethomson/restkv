package com.thanethomson.restkv.config;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
@Data
@ConfigurationProperties(prefix = "restkv.multithreading")
public class MultiThreadingConfig {

    private Integer serviceThreads;

    @Bean
    public ListeningExecutorService executorService() {
        return MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(serviceThreads));
    }

}
