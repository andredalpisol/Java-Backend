package com.soulcode.Servicos.Controllers;


import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Endereco;
import com.soulcode.Servicos.Services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/servicos")

public class EnderecosController {

    @Autowired
    EnderecoService enderecoService;


    @GetMapping("/enderecos")
    public List <Endereco> mostrarEnderecos(){
        return this.enderecoService.mostrarEnderecos();
    }

    @GetMapping("/enderecos/{idEndereco}")
    public Endereco mostrarEnderecoID (@PathVariable Integer idEndereco){
        return this.enderecoService.mostrarEnderecoID(idEndereco);
    }

    @DeleteMapping("/enderecos/{idEndereco}")
    public void deletarEndereco(@PathVariable Integer idEndereco){
        this.enderecoService.deletarEndereco(idEndereco);
    }


    @PostMapping("/enderecos/{idCliente}")
    public ResponseEntity<Endereco> adicionarEndereco (@RequestBody Endereco endereco, @PathVariable Integer idCliente) {
        try {
        this.enderecoService.adicionarEndereco(endereco, idCliente);
        URI novaURI = ServletUriComponentsBuilder.fromCurrentRequest().path("id").buildAndExpand(endereco.getIdEndereco()).toUri();
        return ResponseEntity.created(novaURI).body(endereco);}
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping ("/enderecos/{idEndereco}")
    public ResponseEntity<Endereco> alterarEndereco(@RequestBody Endereco endereco, @PathVariable Integer idEndereco){
        try {
        this.enderecoService.alterarEndreco(endereco, idEndereco);
        return ResponseEntity.ok().body(endereco);
    }
        catch (Exception e){
        return ResponseEntity.badRequest().build();}
    }
}
