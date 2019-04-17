package com.javasampleapproach.batchcsvpostgresql.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.javasampleapproach.batchcsvpostgresql.dao.CustomerDao;
import com.javasampleapproach.batchcsvpostgresql.model.Customer;
import com.javasampleapproach.batchcsvpostgresql.step.Listener;
import com.javasampleapproach.batchcsvpostgresql.step.Processor;
import com.javasampleapproach.batchcsvpostgresql.step.Reader;
import com.javasampleapproach.batchcsvpostgresql.step.Writer;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public CustomerDao customerDao;

	@Bean
	public Job job() {
		return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).listener(new Listener(customerDao))
				.flow(step1()).end().build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<Customer, Customer>chunk(500)
				.reader(Reader.reader("import_test.csv"))
				.processor(new Processor()).writer(new Writer(customerDao)).build();
	}
}
