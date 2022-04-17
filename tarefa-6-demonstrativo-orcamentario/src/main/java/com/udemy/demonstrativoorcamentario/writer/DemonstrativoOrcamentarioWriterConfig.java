package com.udemy.demonstrativoorcamentario.writer;

import com.udemy.demonstrativoorcamentario.entity.GrupoLancamento;
import com.udemy.demonstrativoorcamentario.entity.Lancamento;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

@Configuration
public class DemonstrativoOrcamentarioWriterConfig {

    @Bean
    public ItemWriter<GrupoLancamento> getDemonstrativoOrcamentarioWriter() {
        return itens -> {
            System.out.println("---- Demonstrativo Orçamentário ----");
            for (GrupoLancamento grupo : itens) {
                System.out.printf("[%d] %s - %s%n", grupo.getCodigoNaturezaDespesa(),
                        grupo.getDescricaoNaturezaDespesa(),
                        NumberFormat.getCurrencyInstance().format(grupo.getTotal()));
                for (Lancamento lancamento : grupo.getLancamentos()) {
                    System.out.printf("\t [%s] %s - %s%n", new SimpleDateFormat("dd/MM/yyyy").format(lancamento.getData()), lancamento.getDescricao(),
                            NumberFormat.getCurrencyInstance().format(lancamento.getValor()));
                }
            }
        };
    }
}
