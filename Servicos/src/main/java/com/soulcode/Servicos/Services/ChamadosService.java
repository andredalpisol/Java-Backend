package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Enum.StatusChamado;
import com.soulcode.Servicos.Models.Chamados;
import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.ChamadoRepository;
import com.soulcode.Servicos.Repositories.ClienteRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadosService {

    @Autowired
    ChamadoRepository chamadoRepository;

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Cacheable("chamadoCache")
    public List<Chamados> mostrarTodosChamados() {
        return chamadoRepository.findAll();
    }

    @Cacheable(value = "chamadoCache", key = "#idChamado")
    public Chamados mostrarUmChamado(Integer idChamado){
        Optional<Chamados> chamado = chamadoRepository.findById(idChamado);
        return chamado.orElseThrow();
    }
    @Cacheable (value = "chamadoCache", key = "#idCliente")
    public List<Chamados> findChamadosByCliente(Integer idCliente){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        return chamadoRepository.findByCliente(cliente);
    }

    @Cacheable (value = "chamadoCache", key = "#idFuncionario")
    public List<Chamados> findChamadosByFuncionario(Integer idFuncionario){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return chamadoRepository.findByFuncionario(funcionario);
    }

    @Cacheable(value = "chamadoCache", key ="#stats")
    public List<Chamados> findByStatus(String stats){
        return chamadoRepository.findByStatus(stats);
    }

    @Cacheable(value = "chamadoCache", key = "T(java.util.Objects).hash(#data1, #data2)")
    public List<Chamados> findByDate(Date data1, Date data2){
        return chamadoRepository.findByDate(data1, data2);
    }

    // CADASTRAR UM NOVO CHAMADO TEMOS 2 REGRAS:
    // 1- NO MOMENTO DO CADASTRO DO CHAMADO, JÁ DEVEMOS INFORMAR QUAL CLIENTE O REALIZOU
    // 2- NO MOMENTO DO CADASTRO DO CHAMADO, NÃO VAMOS ATRIBUIR A UM FUNCIONARIO
    // 3- NO MOMENTO DO CADASTRO DO CHAMADO, O STATUS DEVE SER RECEBIDO

    @CachePut(value="chamadoCache", key = "#chamado.idChamado")
    public Chamados cadastrarChamado(Chamados chamado, Integer idCliente){
        chamado.setStatus(StatusChamado.RECEBIDO);
        chamado.setFuncionario(null);
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        chamado.setCliente(cliente.get());
        return chamadoRepository.save(chamado);
    }

    @CacheEvict(value = "chamadoCache", key = "#idChamado")
    public void excluirChamado(Integer id){
        this.chamadoRepository.deleteById(id);
    }

    @CachePut(value = "chamadoCache", key = "#idChamado")
    public Chamados alterarChamado(Chamados chamado, Integer idChamado){

        Chamados chamadoAntigo = mostrarUmChamado(idChamado);
        chamado.setCliente(chamadoAntigo.getCliente());
        chamado.setFuncionario(chamadoAntigo.getFuncionario());
        return chamadoRepository.save(chamado);
    }

    @CachePut(value = "chamadoCache", key = "#idChamado")
    public Chamados atribuirChamado(Integer idChamado, Integer idFuncionario){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        Chamados chamado = mostrarUmChamado(idChamado);
        chamado.setFuncionario(funcionario.get());
        chamado.setStatus(StatusChamado.ATRIBUIDO);

        return chamadoRepository.save(chamado);
    }
    @CachePut(value = "chamadoCache", key = "#idChamado")
    public Chamados alterarStatus (Integer idChamado, String status){
        Chamados chamado = mostrarUmChamado(idChamado);
        if (chamado.getFuncionario() != null){
        switch (status){

            case "ATRIBUIDO":
            {
                chamado.setStatus(StatusChamado.ATRIBUIDO);
                break;
            }
            case "CONCLUIDO":
            {
                chamado.setStatus(StatusChamado.CONCLUIDO);
                break;
            }
        }

    }
        switch(status){

        case "ARQUIVADO":{
            chamado.setStatus(StatusChamado.ARQUIVADO);
            break;
        }

        case "RECEBIDO": {
            chamado.setStatus(StatusChamado.RECEBIDO);
            break;
            }


        }

        return chamadoRepository.save(chamado);
    }
}
