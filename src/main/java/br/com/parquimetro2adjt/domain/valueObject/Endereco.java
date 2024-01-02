package br.com.parquimetro2adjt.domain.valueObject;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Endereco {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String numero;
    private Double latitude;
    private Double longitude;
}

