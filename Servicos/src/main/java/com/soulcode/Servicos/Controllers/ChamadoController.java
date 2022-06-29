package com.soulcode.Servicos.Controllers;



import com.soulcode.Servicos.Models.Chamados;
import com.soulcode.Servicos.Services.ChamadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("servicos")

public class ChamadoController {

    @Autowired
    ChamadosService chamadosService;

    @GetMapping("/chamados")

    public List<Chamados> mostrarTodosChamados(){
    List<Chamados> chamados = chamadosService.mostrarTodosChamados();
    return chamados;
    }

    @GetMapping("chamados/{idChamado}")
    public ResponseEntity<Chamados> mostrarChamadoId (@PathVariable Integer idChamado){
        Chamados chamado = chamadosService.mostrarUmChamado(idChamado);
        return ResponseEntity.ok().body(chamado);
    }

    @GetMapping("chamadosPeloCliente/{idCliente}")
    public List<Chamados> findChamadosByCliente(@PathVariable Integer idCliente){
        List<Chamados> listaChamados = chamadosService.findChamadosByCliente(idCliente);
        return listaChamados;
    }

    @GetMapping("chamadosPeloFuncionario/{idFuncionario}")
    public List <Chamados> findByFuncionario(@PathVariable Integer idFuncionario){
        List<Chamados> chamados = chamadosService.findChamadosByFuncionario(idFuncionario);
        return chamados;
    }

    @GetMapping("chamadosPeloStatus")
    public List<Chamados> findByStatus(@RequestParam ("status") String status){
        List<Chamados> chamados = chamadosService.findByStatus(status);
        return chamados;
    }

    @GetMapping("chamadosPelaData")
    public List<Chamados> findByDate(@RequestParam ("data1") @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE) Date data1,
                                     @RequestParam ("data2") @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE) Date data2)
    {
        List<Chamados> chamados = chamadosService.findByDate(data1, data2);
        return chamados;
    }

    @PostMapping("chamados/{idCliente}")
    public ResponseEntity <Chamados> cadastrarChamado(@RequestBody Chamados chamado, @PathVariable Integer idCliente){
        chamado = chamadosService.cadastrarChamado(chamado, idCliente);
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(chamado.getIdChamado()).toUri();
        return ResponseEntity.created(novaUri).body(chamado);
    }

    @DeleteMapping("chamados/{id}")
    public void excluirChamado(@PathVariable Integer id){
        chamadosService.excluirChamado(id);
    }

    @PutMapping("chamados/{id}")
    public ResponseEntity<Chamados> alterarChamado(@PathVariable Integer id, @RequestBody Chamados chamado){
        chamado.setId(id);
        chamado = chamadosService.alterarChamado(chamado, id);
        return ResponseEntity.ok().body(chamado);
    }

    @PutMapping("chamados/{idChamado}/{idFuncionario}")
    public ResponseEntity <Chamados> atribuirChamado (@PathVariable Integer idChamado, @PathVariable Integer idFuncionario){
        chamadosService.atribuirChamado(idChamado, idFuncionario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("chamadosModificar/{idChamado}")
    public ResponseEntity<Chamados> alterarStatus(@PathVariable Integer idChamado, @RequestParam("status") String status){
        chamadosService.alterarStatus(idChamado, status);
        return ResponseEntity.ok().build();
    }

}
