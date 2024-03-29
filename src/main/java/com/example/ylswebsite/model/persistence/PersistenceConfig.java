package com.example.ylswebsite.model.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/TEST?createDatabaseIfNotExist=true&useUnicode=yes&characterEncoding=UTF-8");
        dataSource.setUsername("");
        dataSource.setPassword("");
        return dataSource;
    }
}
