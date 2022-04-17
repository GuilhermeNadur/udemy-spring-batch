package com.udemy.parimparjob.reader;

import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ParImparReaderConfig {

    @Bean
    public IteratorItemReader<Integer> contaAteDezReader() {
        return new IteratorItemReader<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).iterator());
    }
}
