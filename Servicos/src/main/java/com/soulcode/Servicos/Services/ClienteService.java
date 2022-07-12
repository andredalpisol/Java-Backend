package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Endereco;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.ClienteRepository;
import com.soulcode.Servicos.Repositories.EnderecoRepository;
import com.soulcode.Servicos.Services.Execption.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    public List<Cliente> mostrarTodosClientes() {return clienteRepository.findAll();}

    public Cliente mostrarClientPorId(Integer idCliente){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        return cliente.orElseThrow(() -> new EntityNotFoundException("Erro, funcionario de ID " + idCliente + " não existe"));
    }

    public Cliente cadastrarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public void deletarCliente(Integer id){
        clienteRepository.deleteById(id);
    }

    public void editarCliente(Cliente cliente){
        clienteRepository.save(cliente);
    }
}
