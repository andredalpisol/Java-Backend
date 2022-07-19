package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Repositories.ClienteRepository;
import com.soulcode.Servicos.Repositories.EnderecoRepository;
import com.soulcode.Servicos.Services.Execption.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EnderecoRepository enderecoRepository;


    @Cacheable("clientesCache") // SÓ VAI CHAMAR O RETURN SE O CACHE EXPIRAR// retorno sera clientesCache::[]
    public List<Cliente> mostrarTodosClientes() {return clienteRepository.findAll();}

    @Cacheable(value = "clientesCache", key = "#idCliente") // retorno sera clientesCache::[id]
    public Cliente mostrarClientPorId(Integer idCliente){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        return cliente.orElseThrow(() -> new EntityNotFoundException("Erro, funcionario de ID " + idCliente + " não existe"));
    }
    @CachePut(value = "clientesCache", key = "#cliente.idCliente")
    public Cliente cadastrarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @CachePut(value = "clientesCache", key = "#cliente.idCliente")
    public Cliente editarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @CacheEvict(value = "clientesCache", key = "#idCliente", allEntries = true)
    public void deletarCliente(Integer id){
        clienteRepository.deleteById(id);
    }

}
