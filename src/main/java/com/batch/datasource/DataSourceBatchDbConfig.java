package com.batch.datasource;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DataSourceBatchDbConfig {

    @Bean
    @Primary
    @ConfigurationProperties("batch.datasource")
    public DataSourceProperties batchDataSourceProperties() {
        return new DataSourceProperties();
    }
    

    @Bean(name = "batchDataSource")
    @Primary
    public DataSource batchDataSource() {
        return batchDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }
    
    @Bean
    @Primary
    public PlatformTransactionManager batchTransactionManager(
            @Qualifier("batchDataSource") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
	
}
