package com.batch.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JdbcConfig {

	@Bean
	public JdbcTemplate jdbcTemplateB(
			@Qualifier("dataSourceB") DataSource dataSource
			) {
		return new JdbcTemplate(dataSource);
	}
	
}
