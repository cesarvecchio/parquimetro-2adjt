package br.com.parquimetro2adjt.application.controller.exceptions;

public class TipoPeriodoEPagamentoDivergentesException extends RuntimeException {
    public TipoPeriodoEPagamentoDivergentesException(String message) {
        super(message);
    }

    public TipoPeriodoEPagamentoDivergentesException(String message, Throwable cause) {
        super(message, cause);
    }
}
