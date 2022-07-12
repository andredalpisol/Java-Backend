package com.soulcode.Servicos.Services.Execption;

public class DataIntegrityViolationException extends RuntimeException{

    public DataIntegrityViolationException(String msg){
        super(msg);
    }
}
