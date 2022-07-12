package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
