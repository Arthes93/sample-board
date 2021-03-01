package com.example.sampleboard.config;

import com.example.sampleboard.factory.YamlPropertySourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@Profile("prod")
//@PropertySource(value = "classpath:application-h2.yml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
@PropertySource(value = "file:${HOME}/deploy/sample-board/env/application-mysql.yml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
public class DBConfigByYml {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }
}
