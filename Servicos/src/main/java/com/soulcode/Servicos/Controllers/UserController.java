package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/servicos")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/usuarios")
    private List<User> listarUsuarios(){
        return userService.listarUsuarios();
    }

    @PostMapping("/usuarios")
    private ResponseEntity<User> listarUsuario(@RequestBody User usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.cadastrar(usuario));
    }


}
