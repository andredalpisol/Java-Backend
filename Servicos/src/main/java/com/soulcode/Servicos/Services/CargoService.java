package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Repositories.CargoRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class CargoService {
    @Autowired
    CargoRepository cargoRepository;
    @Autowired
    FuncionarioRepository funcionarioRepository;


    //FUNÇÃO GET
    @Cacheable("cargosCache")
    public List<Cargo> listarCargos(){
        return cargoRepository.findAll();
    }

    @Cacheable(value = "cargosCache", key = "#idCargo")
    public Cargo cargoById (Integer idCargo){
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        return cargo.orElseThrow();
    }
    //FUNÇÃO DELETE
    @CacheEvict(value = "cargosCache", key = "#id", allEntries = true)
    public void deletarCargo(Integer id) {
        cargoRepository.deleteById(id);
    }

    //FUNÇÃO PUT
    @CachePut(value = "cargosCache", key = "#cargo.idCargo")
    public Cargo editarCargo(Cargo cargo){ return cargoRepository.save(cargo);}

    //FUNÇÃO POST
    @CachePut(value = "cargosCache", key = "#cargo.idCargo")
    public Cargo cadastrarCargo (Cargo cargo){
        return cargoRepository.save(cargo);
    }

}
