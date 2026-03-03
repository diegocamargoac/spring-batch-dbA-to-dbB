package com.batch.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.batch.dto.ClienteB;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobCompletionNotificacionListener implements JobExecutionListener {

	private final JdbcTemplate jdbcTemplate;
	
    public JobCompletionNotificacionListener(
            @Qualifier("jdbcTemplateB") JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("Job finalizado, verificando datos:");
			
			jdbcTemplate
				.query("SELECT id, fullname, email, activo FROM clientes", new DataClassRowMapper<>(ClienteB.class))
				.forEach(cliente -> log.info("Cliente en db: " + cliente.getId() + ", " + cliente.getFullName() + ", " + cliente.getEmail() + ", " + cliente.getActivo()));
		}
	}
	
}
