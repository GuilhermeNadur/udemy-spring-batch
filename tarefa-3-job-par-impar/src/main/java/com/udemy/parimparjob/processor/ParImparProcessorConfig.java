package com.udemy.parimparjob.processor;

import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParImparProcessorConfig {

    @Bean
    public FunctionItemProcessor<Integer, String> parOuImparProcessor() {
        return new FunctionItemProcessor<>(item -> item % 2 == 0 ? String.format("%s é PAR", item) : String.format("%s é ÍMPAR", item));
    }
}
