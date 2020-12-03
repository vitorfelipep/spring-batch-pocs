package com.springbatch.jdbcpagingreader.reader;

import com.springbatch.jdbcpagingreader.dominio.Cliente;
import com.springbatch.jdbcpagingreader.exception.BusinessException;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class JdbcPagingReaderConfig {

	@Bean
	public JdbcPagingItemReader<Cliente> jdbcPagingReader(
			@Qualifier("appDataSource") DataSource dataSource,
			PagingQueryProvider queryProvider
			) {

		return new JdbcPagingItemReaderBuilder<Cliente>()
				.name("jdbcPagingReader")
				.dataSource(dataSource)
				.queryProvider(queryProvider)
				.pageSize(5)
				//.rowMapper(new BeanPropertyRowMapper<>(Cliente.class))
				.rowMapper(rowMapper())
				.build();

	}

	private RowMapper<Cliente> rowMapper() {
		return new RowMapper<Cliente>() {
			@Override
			public Cliente mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				if (resultSet.getRow() == 11)
					throw new BusinessException(
							String.format("Encerrando a execução - cliente inválido %s",
									resultSet.getString("email"))
					);
				return clienteRowMapper(resultSet);
			}

			private Cliente clienteRowMapper(ResultSet resultSet) throws SQLException {
				Cliente cliente = new Cliente();
				cliente.setNome(resultSet.getString("nome"));
				cliente.setSobrenome(resultSet.getString("sobrenome"));
				cliente.setIdade(resultSet.getString("idade"));
				cliente.setEmail(resultSet.getString("email"));

				return cliente;
			}
		};
	}

	@Bean
	public SqlPagingQueryProviderFactoryBean queryProviderFactoryBean(
			@Qualifier("appDataSource")	DataSource dataSource
	) {

		SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
		queryProvider.setDataSource(dataSource);
		queryProvider.setSelectClause("select *");
		queryProvider.setFromClause("from cliente");
		queryProvider.setSortKey("email");

		return queryProvider;

	}
}
