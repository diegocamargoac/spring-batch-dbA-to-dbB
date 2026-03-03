package com.batch.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class DataSourceAConfig {

	@Bean
	@ConfigurationProperties("app.datasource-a")
	public DataSourceProperties dataSourceAProperties() {
		return new DataSourceProperties();
	}
	
	@Bean(name = "dataSourceA")
	public DataSource dataSourceA() {
		return dataSourceAProperties().initializeDataSourceBuilder().build();
	}
	
	@Bean
	public DataSourceTransactionManager transactionManagerA(
	        @Qualifier("dataSourceA") DataSource dataSource
	        ) {
	    return new DataSourceTransactionManager(dataSource);
	}
	
}
