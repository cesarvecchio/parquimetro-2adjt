package br.com.parquimetro2adjt.application.response;

import br.com.parquimetro2adjt.domain.entity.Veiculo;
import br.com.parquimetro2adjt.domain.enums.TipoEstacionamentoEnum;

import java.time.LocalDateTime;

public record EstacionamentoResponseDTO (
        String id,
        TipoEstacionamentoEnum tipoEstacionamento,
        Integer duracaoDesejada,
        LocalDateTime horaInicial,
        LocalDateTime horaFinal,
        Veiculo veiculo,
        String nome,
        String email,
        LocalDateTime dataHoraUltimoAlerta
){
}
