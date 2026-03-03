package com.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.infrastructure.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.batch.dto.ClienteA;
import com.batch.dto.ClienteB;
import com.batch.listener.JobCompletionNotificacionListener;
import com.batch.process.ClienteProcess;

@Configuration
public class BatchConfig {

	// Reader
    @Bean
    public JdbcCursorItemReader<ClienteA> reader(
            @Qualifier("dataSourceA") DataSource dataSourceA
    ) {

        return new JdbcCursorItemReaderBuilder<ClienteA>()
                .name("clienteReader")
                .dataSource(dataSourceA)
                .sql("SELECT name, lastName, email, activo FROM clientes")
                .beanRowMapper(ClienteA.class)
                .build();
    }
	
	// Processor
    @Bean
	public ClienteProcess processor() {
		return new ClienteProcess();
	}
	
	// Writter
    @Bean
    public JdbcBatchItemWriter<ClienteB> writer(
    		@Qualifier("dataSourceB") DataSource dataSourceB
    		) {
        return new JdbcBatchItemWriterBuilder<ClienteB>()
                .dataSource(dataSourceB)
                .sql("INSERT INTO clientes(fullName, email, activo) VALUES (:fullName, :email, :activo)")
                .beanMapped()
                .build();
    }
    
    // Job
    @Bean
    public Job job(
    		JobRepository jobRepository,
    		Step step1,
    		JobCompletionNotificacionListener listener
    		) {
    	return new JobBuilder("migrarClientesJob", jobRepository)
    			.incrementer(new RunIdIncrementer())
    			.listener(listener)
    			.start(step1)
    			.build();
    }
	
    // Step
    @Bean
    public Step step1(
            JobRepository jobRepository,
            @Qualifier("transactionManagerB") DataSourceTransactionManager transactionManager,
            JdbcCursorItemReader<ClienteA> reader,
            ClienteProcess processor,
            JdbcBatchItemWriter<ClienteB> writer
    ) {

        return new StepBuilder(jobRepository)
                .<ClienteA, ClienteB>chunk(3)
                	.transactionManager(transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    
    
}
