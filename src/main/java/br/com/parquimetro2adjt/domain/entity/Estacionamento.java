package br.com.parquimetro2adjt.domain.entity;

import br.com.parquimetro2adjt.domain.enums.TipoEstacionamentoEnum;
import br.com.parquimetro2adjt.domain.valueObject.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class Estacionamento {

    @Id
    private String id;
    private TipoEstacionamentoEnum tipoEstacionamento;
    private Integer duracaoDesejada;
    private LocalDateTime horaInicial;
    private LocalDateTime horaFinal;
    private Veiculo veiculo;
    private String nome;
    private String email;
    private LocalDateTime dataHoraUltimoAlerta;
    private Double valorCobrado;
}
