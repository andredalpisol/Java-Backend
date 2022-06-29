package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("servicos")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> mostrarTodosClientes() {
        List<Cliente> clientes = clienteService.mostrarTodosClientes();
        return clientes;
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> mostrarClientPorId(@PathVariable Integer id){
        Cliente cliente = clienteService.mostrarClientPorId(id);
        return ResponseEntity.ok().body(cliente);
    }

    @PostMapping("/clientes")
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente){
        cliente = clienteService.cadastrarCliente(cliente);
        URI novaURI = ServletUriComponentsBuilder.fromCurrentRequest().path("id").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(novaURI).body(cliente);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Integer id){
        this.clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> editarCliente (@PathVariable Integer id, @RequestBody Cliente cliente){
        cliente.setId(id);
        clienteService.editarCliente(cliente);
        return ResponseEntity.ok().body(cliente);
    }
}
