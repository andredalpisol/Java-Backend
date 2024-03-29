package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface CargoRepository extends JpaRepository<Cargo, Integer> {

    Optional<Cargo> findById(Integer id);
}
