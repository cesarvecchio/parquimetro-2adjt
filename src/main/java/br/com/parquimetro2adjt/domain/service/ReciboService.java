package br.com.parquimetro2adjt.domain.service;

import br.com.parquimetro2adjt.domain.entity.Estacionamento;
import br.com.parquimetro2adjt.domain.enums.TipoEstacionamentoEnum;
import br.com.parquimetro2adjt.infra.repository.EstacionamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReciboService {
    private final EstacionamentoRepository estacionamentoRepository;

    public ReciboService(EstacionamentoRepository estacionamentoRepository) {
        this.estacionamentoRepository = estacionamentoRepository;
    }

    public String gerarRecibo(String placa){
        Optional<Estacionamento> optional = estacionamentoRepository.buscarEstacionamentoFinalizadoPorPlaca(placa);

        String recibo = String.format(
            "--------------------------------- \n"
            + "Tipo do Estacionamento: %s \n"
            + "Duração Desejada: %d \n"
            + "Hora Inicial: %s \n"
            + "Hora Final: %s\n "
            + "---------------------------------",
            TipoEstacionamentoEnum.VARIAVEL,
            100,
            LocalDateTime.now(),
            LocalDateTime.now());

        return recibo;
    }
}
