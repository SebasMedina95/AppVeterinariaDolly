package com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.uploads;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    //Tomando las variables de las properties
    @Value("${cloudinary.cloud_name}")
    private String cloudName;

    //Tomando las variables de las properties
    @Value("${cloudinary.api_key}")
    private String apiKey;

    //Tomando las variables de las properties
    @Value("${cloudinary.api_secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);
    }

}
