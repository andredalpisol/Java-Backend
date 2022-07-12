package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("servicos")


public class CargoController {

    @Autowired
    CargoService cargoService;


    @GetMapping("/cargos")
    public List<Cargo> listarCargos(){
        return cargoService.listarCargos();
    }

    @GetMapping("cargos/{id}")
    public ResponseEntity<Cargo> findById(@PathVariable Integer id){
       Cargo cargo  = this.cargoService.cargoById(id);
       return ResponseEntity.ok().body(cargo);
    }

    @DeleteMapping("/cargos/{id}")
    public void deletarCargo(@PathVariable Integer id){
        this.cargoService.deletarCargo(id);
    }


    @PutMapping("/cargos/{id}")
    public ResponseEntity<Cargo> editarCargo(@PathVariable Integer id, @RequestBody Cargo cargo){
        cargo.setIdCargo(id);
        this.cargoService.editarCargo(cargo);
        return ResponseEntity.ok().body(cargo);
    }

    @PostMapping("/cargos")
    public ResponseEntity<Cargo> cadastrarCargo (@RequestBody Cargo cargo){
        this.cargoService.cadastrarCargo(cargo);
        return ResponseEntity.ok().body(cargo);
    }
}
