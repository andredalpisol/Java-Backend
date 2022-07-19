package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Enum.StatusPagamento;
import com.soulcode.Servicos.Models.Chamados;
import com.soulcode.Servicos.Models.Pagamento;
import com.soulcode.Servicos.Repositories.ChamadoRepository;
import com.soulcode.Servicos.Repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    ChamadoRepository chamadoRepository;
    //GET
    @Cacheable("pagamentoCache")
    public List<Pagamento> mostrarTodosPagamentos(){
        return this.pagamentoRepository.findAll();
    }

    @Cacheable(value = "pagamentoCache", key = "#idPagamento")
    public Pagamento mostrarPagamentoID(Integer idPagamento){
        Optional<Pagamento> pagamento = pagamentoRepository.findById(idPagamento);
        return pagamento.orElseThrow();
    }

    @Cacheable(value = "pagamentoCache", key = "#status")
    public List<Pagamento> findbyStatus(String status){
        return this.pagamentoRepository.findByStatus(status);
    }

    //POST
    @CachePut(value = "pagamentoCache", key= "#pagamento.idPagamento")
    public Pagamento criarPagamento (Pagamento pagamento, Integer idChamado) throws Exception{
        Optional <Chamados> chamado = chamadoRepository.findById(idChamado);
        if (chamado.isPresent()){
            pagamento.setIdPagamento(idChamado);
            pagamento.setStatusPagamento(StatusPagamento.LANCADO);
            pagamentoRepository.save(pagamento);
            chamado.get().setPagamento(pagamento);
            this.chamadoRepository.save(chamado.get());

            return pagamento;
        }
        else {
            throw new Exception();
        }
    }

    // PUT
    @CachePut(value = "pagamentoCache", key = "#idPagamento")
    public void alterarPagamento (Pagamento pagamento, Integer idPagamento) throws Exception{
        Pagamento pagamentoAntigo = mostrarPagamentoID(idPagamento);
        if (pagamentoAntigo.getIdPagamento().equals(idPagamento)){
            pagamentoRepository.save(pagamento);
        }
        else {
            throw new Exception();
        }

    }
    @CachePut(value = "pagamentoCache", key = "#idPagamento")
    public Pagamento modificarStatusPagamento (Integer idPagamento, String statusPagamento){
        Pagamento pagamento = mostrarPagamentoID(idPagamento);

        switch (statusPagamento){
            case "LANCADO":
                pagamento.setStatusPagamento(StatusPagamento.LANCADO);

            case "QUITADO":
                pagamento.setStatusPagamento(StatusPagamento.QUITADO);
        }
        return pagamentoRepository.save(pagamento);
    }

    public List<Object> mostrarLista () {
        return this.pagamentoRepository.mostrarLista();
    }

    public List<Object> mostrarListaCliente (Integer idCliente) {
        return this.pagamentoRepository.mostrarListaCliente(idCliente);
    }

}
