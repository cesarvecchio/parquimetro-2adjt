package br.com.parquimetro2adjt.application.request;

public record VeiculoRequestDTO(
         String placa,
         String modelo,
         String cor,
         String marca
) {
}
