package com.udemy.demonstrativoorcamentario.step;

import com.udemy.demonstrativoorcamentario.entity.GrupoLancamento;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemonstrativoOrcamentarioStepConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step getDemonstrativoOrcamentarioStep(MultiResourceItemReader<GrupoLancamento> grupoLancamentoReader, ItemWriter<GrupoLancamento> grupoLancamentoWriter) {
        return stepBuilderFactory
                .get("getDemonstrativoOrcamentarioStep")
                .<GrupoLancamento, GrupoLancamento>chunk(1)
                .reader(grupoLancamentoReader)
                .writer(grupoLancamentoWriter)
                .build();
    }
}
