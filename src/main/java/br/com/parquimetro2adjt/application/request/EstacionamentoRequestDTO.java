package br.com.parquimetro2adjt.application.request;

import br.com.parquimetro2adjt.domain.enums.TipoEstacionamentoEnum;

import java.time.LocalDateTime;

public record EstacionamentoRequestDTO(
    TipoEstacionamentoEnum tipoEstacionamento,
    Integer duracaoDesejada,
    LocalDateTime horaInicial
){}
