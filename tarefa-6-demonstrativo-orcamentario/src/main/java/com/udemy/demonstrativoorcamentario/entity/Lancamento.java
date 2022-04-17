package com.udemy.demonstrativoorcamentario.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Lancamento {

    private String descricao;
    private Date data;
    private Double valor;
}
