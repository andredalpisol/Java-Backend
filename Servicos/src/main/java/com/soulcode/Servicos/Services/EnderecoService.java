package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Endereco;
import com.soulcode.Servicos.Repositories.ClienteRepository;
import com.soulcode.Servicos.Repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    ClienteRepository clienteRepository;


    //GET
    @Cacheable("enderecoCache")
    public List<Endereco> mostrarEnderecos(){
        return enderecoRepository.findAll();
    }

    @Cacheable(value = "enderecoCache", key = "#idEndereco")
    public Endereco mostrarEnderecoID (Integer idEndereco){
        Optional<Endereco> endereco = enderecoRepository.findById(idEndereco);
        return endereco.orElseThrow(() -> new RuntimeException("ERRO! Endereço não encontrado"));
    }

    //DELETE
    @CacheEvict(value = "enderecoCache", key = "#idEndereco")
    public void deletarEndereco(Integer idEndereco){
        enderecoRepository.deleteById(idEndereco);
        Optional<Cliente> cliente = clienteRepository.findById(idEndereco);
        cliente.get().setEndereco(null);
        enderecoRepository.deleteById(idEndereco);
    }

    //PUT
    @CachePut(value = "enderecoCache", key = "#endereco.idEndereco")
    public void alterarEndreco(Endereco endereco, Integer idEndereco) throws Exception{
        Endereco enderecoAntigo = mostrarEnderecoID(idEndereco);
        if (enderecoAntigo.getIdEndereco().equals(idEndereco)){
            enderecoRepository.save(endereco);
        }
        else {
            throw new Exception();
        }


    }

    //POST
    @CachePut(value = "enderecoCache", key = "#idCliente")
    public Endereco adicionarEndereco (Endereco endereco, Integer idCliente) throws Exception{
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if (cliente.isPresent()) {
            endereco.setIdEndereco(idCliente);
            enderecoRepository.save(endereco);
            cliente.get().setEndereco(endereco);
            clienteRepository.save(cliente.get());
            return endereco;
        }
        else {
            throw new Exception();
        }
    }
}
