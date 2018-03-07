package com.thanethomson.restkv.config;

import com.thanethomson.restkv.models.RestParams;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "restkv.restParams")
public class RestParamsConfig {

    private Long timeout;
    private Long maxValueLength;
    private String contentType;
    private Boolean debug;

    @Bean
    public RestParams restParams() {
        RestParams params = new RestParams();
        params.setTimeout(timeout);
        params.setMaxValueLength(maxValueLength);
        params.setContentType(contentType);
        return params;
    }

}
