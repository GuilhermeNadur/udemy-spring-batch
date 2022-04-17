package com.springbatch.demonstrativoorcamentario.writer;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;
import com.springbatch.demonstrativoorcamentario.dominio.Lancamento;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.ResourceSuffixCreator;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Configuration
public class DemonstrativoOrcamentarioWriterConfig {

	@Bean
	@StepScope
	public MultiResourceItemWriter<GrupoLancamento> multiDemonstrativoOrcamentarioWriter(@Value("#{jobParameters['demonstrativosOrcamentarios']}") Resource demonstrativosOrcamentarios,
																						 FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter) {
		return new MultiResourceItemWriterBuilder<GrupoLancamento>()
				.name("multiDemonstrativoOrcamentarioWriter")
				.resource(demonstrativosOrcamentarios)
				.delegate(demonstrativoOrcamentarioWriter)
				.resourceSuffixCreator(suffixCreater())
				.itemCountLimitPerResource(1)
				.build();
	}

	private ResourceSuffixCreator suffixCreater() {
		return i -> i + ".txt";
	}

	@Bean
	@StepScope
	public FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter(@Value("#{jobParameters['demonstrativoOrcamentario']}") Resource demonstrativoOrcamentario,
																			   DemonstrativoOrcamentarioRodape rodapeCallback) {
		return new FlatFileItemWriterBuilder<GrupoLancamento>()
				.name("demonstrativoOrcamentarioWriter")
				.resource(demonstrativoOrcamentario)
				.lineAggregator(lineAggregator())
				.headerCallback(cabecalhoCallback())
				.footerCallback(rodapeCallback)
				.build();
	}

	private FlatFileHeaderCallback cabecalhoCallback() {
		return writer -> {
			writer.append(String.format("SISTEMA INTEGRADO: XPTO \t\t\t\t DATA: %s\n", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
			writer.append(String.format("MÓDULO: ORÇAMENTO \t\t\t\t\t\t HORA: %s\n", new SimpleDateFormat("HH:MM").format(new Date())));
			writer.append("\t\t\tDEMONSTRATIVO ORCAMENTARIO\n");
			writer.append("----------------------------------------------------------------------------\n");
			writer.append("CODIGO NOME VALOR\n");
			writer.append("Data Descricao Valor\n");
			writer.append("----------------------------------------------------------------------------\n");
		};
	}

	private LineAggregator<GrupoLancamento> lineAggregator() {
		return grupoLancamento -> {

			StringBuilder stringBuilder = new StringBuilder();
			Locale locale = new Locale("pt", "BR");

			String formatGrupoLancamento = String.format("[%d] %s - %s\n", grupoLancamento.getCodigoNaturezaDespesa(),
					grupoLancamento.getDescricaoNaturezaDespesa(), NumberFormat.getCurrencyInstance(locale).format(grupoLancamento.getTotal()));

			for (Lancamento lancamento : grupoLancamento.getLancamentos()) {
				stringBuilder.append(String.format("\t [%s] %s - %s\n", new SimpleDateFormat("dd/MM/yyyy").format(lancamento.getData()), lancamento.getDescricao(),
						NumberFormat.getCurrencyInstance(locale).format(lancamento.getValor())));
			}

			return formatGrupoLancamento + stringBuilder.toString();
		};
	}
}
