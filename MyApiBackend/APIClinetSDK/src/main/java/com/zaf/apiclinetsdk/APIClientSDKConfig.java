package com.zaf.apiclinetsdk;

import com.zaf.apiclinetsdk.client.UserClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Data
@ComponentScan
@Configuration
@ConfigurationProperties(prefix = "zaf.api")
public class APIClientSDKConfig {
    private String accessKey;
    private String secretKey;
    @Bean
    public UserClient userClient() {
        return new UserClient(accessKey,secretKey);
    }

}
