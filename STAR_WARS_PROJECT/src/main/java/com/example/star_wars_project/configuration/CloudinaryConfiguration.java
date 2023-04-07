package com.example.star_wars_project.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfiguration {
    private final String cloudName;
    private final String apiKey;
    private final String apiSecret;

    public CloudinaryConfiguration(
            @Value("${spring.cloudinary.cloud-name}") String cloudName,
            @Value("${spring.cloudinary.api-key}") String apiKey,
            @Value("${spring.cloudinary.api-secret}") String apiSecret) {
        this.cloudName = cloudName;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public Cloudinary cloudinary() {
        Map config = new HashMap();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);
    }
}