package com.udemy.demonstrativoorcamentario.reader;

import com.udemy.demonstrativoorcamentario.entity.GrupoLancamento;
import com.udemy.demonstrativoorcamentario.entity.Lancamento;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.ArrayList;

@Configuration
public class BancoLancamentoReaderConfig {

    @Bean
    public JdbcCursorItemReader<GrupoLancamento> getBancoLancamentoReader(@Qualifier("getAppDataSource") DataSource appDataSource) {
        return new JdbcCursorItemReaderBuilder<GrupoLancamento>()
                .name("getBancoLancamentoReader")
                .dataSource(appDataSource)
                .sql("SELECT * FROM lancamento")
                .rowMapper(getRowMapper())
                .build();
    }

    private RowMapper<GrupoLancamento> getRowMapper() {
        return (rs, rowNum) -> GrupoLancamento.builder()
                .codigoNaturezaDespesa(rs.getInt("codigoNaturezaDespesa"))
                .descricaoNaturezaDespesa(rs.getString("descricaoNaturezaDespesa"))
                .lancamentoAtualTemporario(Lancamento.builder()
                        .data(rs.getDate("dataLancamento"))
                        .descricao(rs.getString("descricaoLancamento"))
                        .valor(rs.getDouble("valorLancamento"))
                        .build())
                .lancamentos(new ArrayList<>())
                .build();
    }
}
