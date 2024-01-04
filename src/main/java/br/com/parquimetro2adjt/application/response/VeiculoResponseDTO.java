package br.com.parquimetro2adjt.application.response;

public record VeiculoResponseDTO(

        String id,

        String placa,
        String modelo,
        String cor,
        String marca,
        CondutorResponseDTO condutor
) {
}
