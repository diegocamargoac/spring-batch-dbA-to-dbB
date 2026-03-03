package com.batch.dbinit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DbBInitConfig {

	@Bean
	public DataSourceInitializer dataSourceInitializerB(
			@Qualifier("dataSourceB") DataSource dataSource
		    ) {

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

        populator.addScript(new ClassPathResource("schema-b.sql"));

        DataSourceInitializer initializer = new DataSourceInitializer();

        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(populator);

        return initializer;
    }
	
}
