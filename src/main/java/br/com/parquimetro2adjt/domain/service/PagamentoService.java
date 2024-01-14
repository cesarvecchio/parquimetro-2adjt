package br.com.parquimetro2adjt.domain.service;

import br.com.parquimetro2adjt.domain.entity.Estacionamento;
import br.com.parquimetro2adjt.domain.enums.TipoEstacionamentoEnum;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class PagamentoService {

    private static final Double VALOR_POR_HORA = 2.0;

    public Double calcularValorPagamento(Estacionamento estacionamento) {
        if (TipoEstacionamentoEnum.FIXO == estacionamento.getTipoEstacionamento()) {
            return estacionamento.getDuracaoDesejada() * VALOR_POR_HORA;
        }

        Duration duracao = Duration.between(estacionamento.getHoraInicial(), estacionamento.getHoraFinal());

        return (Math.ceil(duracao.toMinutes() / 60.0)) * VALOR_POR_HORA;
    }

}
