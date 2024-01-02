package br.com.parquimetro2adjt.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class Veiculo {

    @Id
    private String id;
    private String placa;
    private String modelo;
    private String cor;
    private String marca;
    private Condutor condutor;
}
