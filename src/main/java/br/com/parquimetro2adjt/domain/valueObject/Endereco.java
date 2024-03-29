package br.com.parquimetro2adjt.domain.valueObject;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

