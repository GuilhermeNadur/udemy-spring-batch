package com.springbatch.contasbancarias.writer;

import com.springbatch.contasbancarias.dominio.Conta;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompositeContaWriterConfig {

    @Bean
    public CompositeItemWriter<Conta> compositeItemWriter(@Qualifier("fileContaWriter") FlatFileItemWriter<Conta> fileContaWriter, JdbcBatchItemWriter<Conta> jdbcContaWriter) {
        return new CompositeItemWriterBuilder<Conta>()
                .delegates(fileContaWriter, jdbcContaWriter)
                .build();
    }
}
