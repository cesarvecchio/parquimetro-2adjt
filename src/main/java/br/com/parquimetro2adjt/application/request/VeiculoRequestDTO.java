package br.com.parquimetro2adjt.application.request;

public record VeiculoRequestDTO(

         String id,
         String placa,
         String modelo,
         String cor,
         String marca,

         CondutorRequestDTO condutorRequestDTO
) {
}
