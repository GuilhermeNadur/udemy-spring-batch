package com.springbatch.contasbancarias.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.contasbancarias.dominio.Conta;

import javax.sql.DataSource;

@Configuration
public class JdbcContaWriterConfig {

	@Bean
	public JdbcBatchItemWriter<Conta> jdbcContaWriter(@Qualifier("appDataSource") DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Conta>()
				.dataSource(dataSource)
				.sql("INSERT INTO conta (tipo, limite, cliente_id) VALUES (?, ?, ?)")
				.itemPreparedStatementSetter(getPreparedStatementSetter())
				.build();
	}

	private ItemPreparedStatementSetter<Conta> getPreparedStatementSetter() {
		return (conta, preparedStatement) -> {
			preparedStatement.setString(1, conta.getTipo().name());
			preparedStatement.setDouble(2, conta.getLimite());
			preparedStatement.setString(3, conta.getClienteId());
		};
	}
}
