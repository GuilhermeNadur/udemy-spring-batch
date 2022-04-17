package com.udemy.demonstrativoorcamentario.reader;

import com.udemy.demonstrativoorcamentario.entity.GrupoLancamento;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class DemonstrativoOrcamentarioReaderConfig {

    @Bean
    @StepScope
    public MultiResourceItemReader<GrupoLancamento> getDemonstrativoOrcamentarioReader(@Value("#{jobParameters['arquivosLancamento']}") Resource[] arquivosLancamento,
                                                                                    GrupoLancamentoReader grupoLancamentoReader) {
        return new MultiResourceItemReaderBuilder<GrupoLancamento>()
                .name("getDemonstrativoOrcamentarioReader")
                .resources(arquivosLancamento)
                .delegate(grupoLancamentoReader)
                .build();
    }
}
