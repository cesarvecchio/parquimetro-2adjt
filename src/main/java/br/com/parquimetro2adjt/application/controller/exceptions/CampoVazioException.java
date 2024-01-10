package br.com.parquimetro2adjt.application.controller.exceptions;

public class CampoVazioException extends RuntimeException{
    public CampoVazioException(String message) {
        super(message);
    }

    public CampoVazioException(String message, Throwable cause) {
        super(message, cause);
    }
}
