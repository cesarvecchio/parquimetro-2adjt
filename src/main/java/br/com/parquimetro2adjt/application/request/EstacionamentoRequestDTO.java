package br.com.parquimetro2adjt.application.request;

import br.com.parquimetro2adjt.domain.enums.TipoEstacionamentoEnum;

public record EstacionamentoRequestDTO(
    TipoEstacionamentoEnum tipoEstacionamento,
    Integer duracaoDesejada
){}
