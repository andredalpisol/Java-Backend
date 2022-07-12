package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.CargoRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public List<Cargo> listarCargos(){
        return cargoRepository.findAll();
    }

    public Cargo cargoById (Integer idCargo){
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        return cargo.orElseThrow();
    }
    //FUNÇÃO DELETE
    public void deletarCargo(Integer id) {
        cargoRepository.deleteById(id);
    }

    //FUNÇÃO PUT
    public void editarCargo(Cargo cargo){ cargoRepository.save(cargo);}

    //FUNÇÃO POST
    public Cargo cadastrarCargo (Cargo cargo){
        return cargoRepository.save(cargo);
    }

}
