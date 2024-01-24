package br.com.parquimetro2adjt.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class Veiculo {

    private String placa;
    private String modelo;
    private String cor;
    private String marca;
}
