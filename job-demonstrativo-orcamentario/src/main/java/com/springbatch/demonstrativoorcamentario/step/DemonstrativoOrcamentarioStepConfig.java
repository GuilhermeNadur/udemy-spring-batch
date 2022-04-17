package com.springbatch.demonstrativoorcamentario.step;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;
import com.springbatch.demonstrativoorcamentario.reader.GrupoLancamentoReader;
import com.springbatch.demonstrativoorcamentario.writer.DemonstrativoOrcamentarioRodape;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemonstrativoOrcamentarioStepConfig {

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step demonstrativoOrcamentarioStep(GrupoLancamentoReader demonstrativoOrcamentarioReader, MultiResourceItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter,
											  DemonstrativoOrcamentarioRodape demonstrativoOrcamentarioRodape) {
		return stepBuilderFactory
				.get("demonstrativoOrcamentarioStep")
				.<GrupoLancamento,GrupoLancamento>chunk(1)
				.reader(demonstrativoOrcamentarioReader)
				.writer(demonstrativoOrcamentarioWriter)
				.listener(demonstrativoOrcamentarioRodape)
				.build();
	}
}
