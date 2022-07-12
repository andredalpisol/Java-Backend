package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.Pagamento;
import com.soulcode.Servicos.Services.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/servicos")
public class PagamentoController{

    @Autowired
    PagamentoService pagamentoService;

    @GetMapping("/pagamentos")
    private List<Pagamento> mostrarTodosPagamentos(){
        return this.pagamentoService.mostrarTodosPagamentos();
    }

    @GetMapping("/pagamentos/{idPagamento}")
    private ResponseEntity<Pagamento> mostrarPagamentoID(@PathVariable Integer idPagamento){
        Pagamento pagamento = this.pagamentoService.mostrarPagamentoID(idPagamento);
        return ResponseEntity.ok().body(pagamento);
    }

    @GetMapping("/pagamentoStatus")
    private List<Pagamento> findbyStatus (@RequestParam ("status") String status){
        return pagamentoService.findbyStatus(status);
    }

    @GetMapping("/pagamentosLista")
    private List<Object> mostrarLista() {
        return pagamentoService.mostrarLista();
    }

    @GetMapping("/pagamentosListaCliente/{idCliente}")
    private List<Object> mostrarListaCliente (@PathVariable Integer idCliente){
        return pagamentoService.mostrarListaCliente(idCliente);
    }

    @PostMapping("/pagamentos/{idChamado}")
    private ResponseEntity<Pagamento> criarPagamento(@RequestBody Pagamento pagamento, @PathVariable Integer idChamado){
        try {
        this.pagamentoService.criarPagamento(pagamento, idChamado);
        URI novaURI = ServletUriComponentsBuilder.fromCurrentRequest().path("id").buildAndExpand(pagamento.getIdPagamento()).toUri();
        return ResponseEntity.created(novaURI).body(pagamento);}
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/pagamentos/{idPagamento}")
    private ResponseEntity<Object> alterarPagamento (@PathVariable Integer idPagamento, @RequestBody Pagamento pagamento) {
        try {
            this.pagamentoService.alterarPagamento(pagamento, idPagamento);
            return ResponseEntity.ok().body(pagamento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERRO DOS GURI PAEEEEEEEEEE"); //AQUI PASSAMOS ERRO PERSONALIZADO;
        }
    }
    @PutMapping("/pagamentosAlterarStatus/{id}")
    private ResponseEntity <Pagamento> modificarStatusPagamento(@PathVariable Integer idPagamento, @RequestParam ("status") String statusPagamento){
        return ResponseEntity.ok().body(pagamentoService.modificarStatusPagamento(idPagamento, statusPagamento));
    }

}




