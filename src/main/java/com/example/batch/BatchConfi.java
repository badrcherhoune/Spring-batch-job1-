package com.example.batch;


import java.util.Arrays;
import java.util.List;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.batch.entities.Person;

@Configuration
@EnableBatchProcessing
public class BatchConfi {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
    private StepBuilderFactory stepBuilderFactory;
	
	
	@Bean
	public ItemReader<Person> reader() {
        List<Person> persons = Arrays.asList(
                new Person(null,"Alice", 25),
                new Person(null,"Bob", 30),
                new Person(null,"Charlie", 35)
        );

        return new ListItemReader<>(persons);
    }
	
	@Bean
    public ItemProcessor<Person, String> processor() {
        return person -> "Hello, " + person.getName();
    }
	
	@Bean
    public ItemWriter<String> writer() {
        return items -> {
            for (String item : items) {
                System.out.println(item);
            }
        };
    }
	
	@Bean
    public Step step1(ItemReader<Person> reader, ItemProcessor<Person, String> processor, ItemWriter<String> writer) {
        return stepBuilderFactory.get("step1")
                .<Person, String>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
	
	@Bean
    public Job greetingJob(Step step1) {
        return jobBuilderFactory.get("greetingJob")
                .start(step1)
                .build();
    }

}
