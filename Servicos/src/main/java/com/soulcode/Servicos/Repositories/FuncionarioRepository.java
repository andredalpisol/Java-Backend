package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    Optional<Funcionario> findByEmail(String Email);
    Optional<Funcionario> findByNome (String nome);

    List<Funcionario> findByCargo (Optional<Cargo> cargo);

}
