package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PagamentoRepository extends JpaRepository <Pagamento, Integer> {

    @Query(value = "SELECT * FROM pagamento WHERE status_pagamento =:status", nativeQuery = true)
    List<Pagamento> findByStatus(String status);

    @Query(value = "SELECT pagamento.*, chamados.id_chamado, chamados.titulo, cliente.id, cliente.nome\n" +
            "FROM chamados RIGHT JOIN pagamento ON chamados.id_chamado = pagamento.id_pagamento\n" +
            "LEFT JOIN cliente ON cliente.id = chamados.id_cliente", nativeQuery = true)
    List<Object> mostrarLista ();

    @Query(value = "SELECT pagamento.*, chamados.id_chamado, chamados.titulo, cliente.id, cliente.nome\n" +
            "FROM chamados RIGHT JOIN pagamento ON chamados.id_chamado = pagamento.id_pagamento\n" +
            "LEFT JOIN cliente ON cliente.id = chamados.id_cliente WHERE cliente.id =:idCliente", nativeQuery = true)
    List<Object> mostrarListaCliente (Integer idCliente);
}
