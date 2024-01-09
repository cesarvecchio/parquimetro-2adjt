package br.com.parquimetro2adjt.domain.entity;

import br.com.parquimetro2adjt.domain.enums.TipoEstacionamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estacionamento {
    @Id
    private ObjectId id;
    private TipoEstacionamentoEnum tipoEstacionamento;
    private Integer duracaoDesejada;
    private LocalDateTime horaInicial;
    private LocalDateTime horaFinal;
    private Veiculo veiculo;
}
