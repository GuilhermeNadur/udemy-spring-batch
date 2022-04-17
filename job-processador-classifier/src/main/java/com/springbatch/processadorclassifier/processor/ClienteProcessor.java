package com.springbatch.processadorclassifier.processor;

import com.springbatch.processadorclassifier.dominio.Cliente;
import org.springframework.batch.item.ItemProcessor;

public class ClienteProcessor implements ItemProcessor<Cliente, Cliente> {

    @Override
    public Cliente process(Cliente cliente) throws Exception {
        System.out.printf("\nAplicando regras de negócio para o cliente %s%n", cliente.getEmail());
        return cliente;
    }
}
