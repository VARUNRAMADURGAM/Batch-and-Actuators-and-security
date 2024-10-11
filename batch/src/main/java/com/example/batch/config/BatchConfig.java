package com.example.batch.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;

import com.example.batch.model.Person;
import com.example.batch.model.Person1;
import com.example.batch.processor.PersonProcessor;
import com.example.batch.processor.PersonToPerson1Processor;
import com.example.batch.repository.Person1Repository;
import com.example.batch.repository.PersonRepository;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private Person1Repository person1Repository;

	@Bean
	public FlatFileItemReader<Person> reader() {
		FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("persons1.csv"));
		reader.setLinesToSkip(1);
		DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("name", "birthDate");

		BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Person.class);

		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);

		reader.setLineMapper(lineMapper);
		return reader;
	}

	@Bean
	public ItemProcessor<Person, Person> processor() {
		return new PersonProcessor();
	}

	@Bean
	public RepositoryItemWriter<Person> writer() {
		RepositoryItemWriter<Person> repositoryItemWriter = new RepositoryItemWriter<>();
		repositoryItemWriter.setRepository(personRepository);
		repositoryItemWriter.setMethodName("save");
		return repositoryItemWriter;
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<Person, Person>chunk(10).reader(reader()).processor(processor())
				.writer(writer()).build();
	}

	// Step 2 reads from Person table and writes to Person1 table
	@Bean
	public RepositoryItemReader<Person> personArchieveReader() {
		RepositoryItemReader<Person> arcPersonReader = new RepositoryItemReader<>();
		arcPersonReader.setRepository(personRepository); 
		arcPersonReader.setMethodName("findAll");
		Map<String, Sort.Direction> sortMap = new HashMap<String, Sort.Direction>();
	    sortMap.put("id", Sort.Direction.ASC); 
	    arcPersonReader.setSort(sortMap);

		return arcPersonReader;
	}

	@Bean
	public ItemProcessor<Person, Person1> personArchiveprocessor() {
		return new PersonToPerson1Processor();
	}

	@Bean
	public ItemWriter<Person1> personArchiveWriter() {
		RepositoryItemWriter<Person1> writer = new RepositoryItemWriter<>();
		writer.setRepository(person1Repository);
		writer.setMethodName("save");
		return writer;
	}

	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2").<Person, Person1>chunk(10).reader(personArchieveReader())
				.processor(personArchiveprocessor()).writer(personArchiveWriter()).build();
	}

	@Bean
	public Job importUserJob(Step step1, Step step2) {
		return jobBuilderFactory.get("importUserJob").start(step1).next(step2).build();
	}
}
