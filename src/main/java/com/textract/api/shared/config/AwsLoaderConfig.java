package com.textract.api.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;

@Configuration
public class AwsLoaderConfig {

    @Bean
    public AwsCredentialsProvider awsEnvironmentLoader() {
        return AwsCredentialsProviderChain.builder()
                .credentialsProviders(() -> new AwsCredentials() {
                    @Override
                    public String accessKeyId() {
                        return System.getenv("AWS_ACCESS_KEY_ID");
                    }

                    @Override
                    public String secretAccessKey() {
                        return System.getenv("AWS_SECRET_ACCESS_KEY");
                    }
                })
                .reuseLastProviderEnabled(false)
                .build();
    }
}
