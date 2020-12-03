package com.springbatch.arquivomultiplosformatos.reader;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;
import com.springbatch.arquivomultiplosformatos.dominio.Transacao;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

public class ArquivoClienteTransacaoReader implements ItemStreamReader<Cliente> {

    private Object objAtual;
    private ItemStreamReader<Object> delegateReader;

    public ArquivoClienteTransacaoReader(ItemStreamReader<Object> delegateReader) {
        this.delegateReader = delegateReader;
    }

    @Override
    public Cliente read() throws Exception {
        if (objAtual == null)
            objAtual = delegateReader.read();

        Cliente cliente = (Cliente) objAtual;
        objAtual = null;

        if (cliente != null) {
            while (peek() instanceof Transacao)
                cliente.getTransacoes().add((Transacao) objAtual);
        }
        return cliente;
    }

    private Object peek() throws Exception {
        objAtual = delegateReader.read();
        return objAtual;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegateReader.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegateReader.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegateReader.close();
    }
}
