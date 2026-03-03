package com.batch.dbinit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DbAInitConfig {

	@Bean
	public DataSourceInitializer dataSourceInitializerA(
			@Qualifier("dataSourceA") DataSource dataSource
		    ) {

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

        populator.addScript(new ClassPathResource("schema-a.sql"));

        DataSourceInitializer initializer = new DataSourceInitializer();

        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(populator);

        return initializer;
    }
}
