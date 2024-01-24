package br.com.parquimetro2adjt.domain.valueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Veiculo {

    private String placa;
    private String modelo;
    private String cor;
    private String marca;
}
