package br.com.parquimetro2adjt.application.controller.exceptions;

public class CadastradoException extends RuntimeException {
    public CadastradoException(String message){
        super(message);
    }

    public CadastradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
