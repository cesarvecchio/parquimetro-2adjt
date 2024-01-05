package br.com.parquimetro2adjt.application.controller.exceptions;

public class VeiculoNaoPertenceAoCondutorException extends RuntimeException {
    public VeiculoNaoPertenceAoCondutorException(String message) {
        super(message);
    }

    public VeiculoNaoPertenceAoCondutorException(String message, Throwable cause) {
        super(message, cause);
    }
}
