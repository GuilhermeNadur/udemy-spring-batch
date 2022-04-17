package com.springbatch.jdbcpagingreader.reader;

import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.jdbcpagingreader.dominio.Cliente;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
public class JdbcPagingReaderReaderConfig {
	@Bean
	public JdbcPagingItemReader<Cliente> jdbcPagingReader(@Qualifier("appDataSource") DataSource dataSource, PagingQueryProvider pagingQueryProvider) {
		return new JdbcPagingItemReaderBuilder<Cliente>()
				.name("jdbcPagingReader")
				.dataSource(dataSource)
				.queryProvider(pagingQueryProvider)
				.pageSize(1)
				.rowMapper(new BeanPropertyRowMapper<Cliente>(Cliente.class))
				.build();
	}

	@Bean
	public SqlPagingQueryProviderFactoryBean pagingQueryProvider(@Qualifier("appDataSource") DataSource dataSource) {

		SqlPagingQueryProviderFactoryBean pagingQueryProvider = new SqlPagingQueryProviderFactoryBean();
		pagingQueryProvider.setDataSource(dataSource);
		pagingQueryProvider.setSelectClause("SELECT *");
		pagingQueryProvider.setFromClause("FROM cliente");
		pagingQueryProvider.setSortKey("email");

		return pagingQueryProvider;
	}
}
