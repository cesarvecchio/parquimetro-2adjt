package br.com.parquimetro2adjt.domain.entity;

import br.com.parquimetro2adjt.domain.enums.TipoEstacionamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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
