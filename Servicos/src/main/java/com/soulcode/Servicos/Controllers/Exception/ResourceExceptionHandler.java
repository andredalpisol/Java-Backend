package com.soulcode.Servicos.Controllers.Exception;

import com.soulcode.Servicos.Services.Execption.DataIntegrityViolationException;
import com.soulcode.Servicos.Services.Execption.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandartError> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
        StandartError erro = new StandartError();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.NOT_FOUND.value());
        erro.setError("Registro não encontrado");
        erro.setMessage(e.getMessage());
        erro.setTrace("EntityNotFoundException");
        erro.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandartError> dataIntegrityViolation(DataIntegrityViolationException e, HttpServletRequest request){
        StandartError erro = new StandartError();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.CONFLICT.value());
        erro.setError("ERRO! Atributo informado está errado");
        erro.setMessage(e.getMessage());
        erro.setTrace("DataIntegrityViolationException");
        erro.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }


}
