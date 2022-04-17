package com.udemy.demonstrativoorcamentario.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GrupoLancamento {

    private Integer codigoNaturezaDespesa;
    private String descricaoNaturezaDespesa;
    private List<Lancamento> lancamentos;
    private Lancamento lancamentoAtualTemporario;

    public Double getTotal() {
        return lancamentos
                .stream()
                .mapToDouble(Lancamento::getValor)
                .reduce(0.0, Double::sum);
    }
}
