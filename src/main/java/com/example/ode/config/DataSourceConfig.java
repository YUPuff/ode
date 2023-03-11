package com.example.ode.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * 获取数据源
 * @Author: lyl
 * @Date: 2023-03-09 17:21
 **/

//@Data
//@Configuration
//@ConfigurationProperties(prefix = "spring.datasource")
//@PropertySource("application.yml")
//public class DataSourceConfig {
//    private String name;
//    private String url;
//    private String username;
//    private String password;
//    private String driverClassName;
//
//    @Bean
//    public DataSource dataSource(){
//        MysqlDataSource dataSource = new MysqlDataSource();
//        dataSource.setUrl(getUrl());
//        dataSource.setUser(getUsername());
//        dataSource.setPassword(getPassword());
//        return dataSource;
//    }
//
//}
