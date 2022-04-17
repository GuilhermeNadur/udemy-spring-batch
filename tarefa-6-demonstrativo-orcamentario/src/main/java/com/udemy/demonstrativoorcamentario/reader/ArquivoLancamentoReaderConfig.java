package com.udemy.demonstrativoorcamentario.reader;

import com.udemy.demonstrativoorcamentario.entity.GrupoLancamento;
import com.udemy.demonstrativoorcamentario.entity.Lancamento;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ArquivoLancamentoReaderConfig {

    @Bean
    public FlatFileItemReader<GrupoLancamento> getArquivoLancamentoReader() {
        return new FlatFileItemReaderBuilder<GrupoLancamento>()
                .name("getArquivoLancamentoReader")
                .delimited()
                .names("codigoNaturezaDespesa", "descricaoNaturezaDespesa", "descricaoLancamento", "dataLancamento", "valorLancamento")
                .fieldSetMapper(getGrupoLancamentoFieldSetMapper())
                .build();
    }

    private FieldSetMapper<GrupoLancamento> getGrupoLancamentoFieldSetMapper() {
        return fieldSet -> GrupoLancamento.builder()
                .codigoNaturezaDespesa(fieldSet.readInt("codigoNaturezaDespesa"))
                .descricaoNaturezaDespesa(fieldSet.readString("descricaoNaturezaDespesa"))
                .lancamentoAtualTemporario(Lancamento.builder()
                        .data(fieldSet.readDate("dataLancamento"))
                        .descricao(fieldSet.readString("descricaoLancamento"))
                        .valor(fieldSet.readDouble("valorLancamento"))
                        .build())
                .lancamentos(new ArrayList<>())
                .build();
    }
}
