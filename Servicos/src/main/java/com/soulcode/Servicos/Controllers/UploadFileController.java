package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Services.FuncionarioService;
import com.soulcode.Servicos.Util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/servicos")

public class UploadFileController {

    @Autowired
    FuncionarioService funcionarioService;

    @PostMapping("/funcionarios/envioFotos/{idFuncionario}")
    //primeiro parametro vai vir pela URL (ID funcionario) - segundo parametro é o arquivo que queremos enviar
    //terceiro é o nome que queremos salvar o arquivo, será passado através dos paramaetros da requisição

    public ResponseEntity <Void> enviarFoto(@PathVariable Integer idFuncionario, MultipartFile file, @RequestParam("nome") String nome){


        String uploadDir = "C:/Users/andre/Desktop/FotoFunc";
        String nomeMaisCaminho = "C:/Users/andre/Desktop/FotoFunc/" + nome;


        try {
            UploadFile.saveFile(uploadDir, nome, file);
            funcionarioService.salvarFoto(idFuncionario, nomeMaisCaminho);
        } catch (IOException e) {
            System.out.println("O arquivo não foi enviado " + e.getMessage());;
        }
        return ResponseEntity.ok().build();
    }
}
