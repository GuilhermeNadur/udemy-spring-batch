package com.udemy.demonstrativoorcamentario.reader;

import com.udemy.demonstrativoorcamentario.entity.GrupoLancamento;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class GrupoLancamentoReader implements ItemStreamReader<GrupoLancamento>, ResourceAwareItemReaderItemStream<GrupoLancamento> {

    @Autowired
    private JdbcCursorItemReader<GrupoLancamento> delegate;

//    @Autowired
//    private FlatFileItemReader<GrupoLancamento> delegate;

    private GrupoLancamento lancamentoAtual;

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegate.close();
    }

    @Override
    public void setResource(Resource resource) {
//        delegate.setResource(resource);
    }

    @Override
    public GrupoLancamento read() throws Exception {
        if (lancamentoAtual == null)
            lancamentoAtual = delegate.read();

        GrupoLancamento grupoLancamento = lancamentoAtual;
        lancamentoAtual = null;

        if (grupoLancamento != null) {
            GrupoLancamento proxLancamento = peek();
            while (isLancamentoRelacionado(grupoLancamento, proxLancamento)) {
                grupoLancamento.getLancamentos().add(lancamentoAtual.getLancamentoAtualTemporario());
                proxLancamento = peek();
            }
            grupoLancamento.getLancamentos().add(grupoLancamento.getLancamentoAtualTemporario());
        }
        return grupoLancamento;
    }

    private boolean isLancamentoRelacionado(GrupoLancamento grupoLancamento, GrupoLancamento proxLancamento) {
        return proxLancamento != null && proxLancamento.getCodigoNaturezaDespesa().equals(grupoLancamento.getCodigoNaturezaDespesa());
    }

    public GrupoLancamento peek() throws Exception {
        lancamentoAtual = delegate.read();
        return lancamentoAtual;
    }
}
