package com.textract.api.shared.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource() {
        String URL = System.getenv("RDS_URL");
        String USER = System.getenv("RDS_USERNAME");
        String PASS = System.getenv("RDS_PASSWORD");

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.hibernate.dialect.PostgreSQLDialect");
        dataSourceBuilder.url(URL);
        dataSourceBuilder.username(USER);
        dataSourceBuilder.password(PASS);
        return dataSourceBuilder.build();
    }
}