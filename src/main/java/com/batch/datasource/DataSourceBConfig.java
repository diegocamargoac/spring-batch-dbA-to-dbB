package com.batch.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class DataSourceBConfig {

	@Bean
	@ConfigurationProperties("app.datasource-b")
	public DataSourceProperties dataSourceBProperties() {
		return new DataSourceProperties();
	}
	
	@Bean(name = "dataSourceB")
	public DataSource dataSourceB() {
		return dataSourceBProperties().initializeDataSourceBuilder().build();
	}
	
	@Bean
	public DataSourceTransactionManager transactionManagerB(
	        @Qualifier("dataSourceB") DataSource dataSource
	        ) {
	    return new DataSourceTransactionManager(dataSource);
	}
	
}
