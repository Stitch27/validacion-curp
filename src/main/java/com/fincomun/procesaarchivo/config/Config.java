package com.fincomun.procesaarchivo.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

@Configuration
public class Config {

    @Bean
    public DataSource dataSourceOracle() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        // dsLookup.setResourceRef(true);
        DataSource dataSource = dsLookup.getDataSource("jdbc/Oracle");
        return dataSource;
    }
    
}
