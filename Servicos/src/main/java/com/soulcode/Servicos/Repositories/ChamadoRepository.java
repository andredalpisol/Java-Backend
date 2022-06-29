package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Chamados;
import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ChamadoRepository extends JpaRepository <Chamados, Integer> {

    List<Chamados> findByCliente(Optional<Cliente> cliente);

    List<Chamados> findByFuncionario(Optional<Funcionario> funcionario);

    //=: é uma variavel, serve para informarmos parametros ao metodo, podemos substituir conforme status que estamos procurando.
    @Query(value = "SELECT * FROM chamados WHERE status =:status", nativeQuery = true)
    List<Chamados> findByStatus(String status);

    @Query(value = "SELECT * FROM chamados WHERE data BETWEEN :data1 AND :data2", nativeQuery = true)
    List<Chamados> findByDate(Date data1, Date data2);
}
