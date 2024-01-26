package br.com.parquimetro2adjt.domain.service;

import br.com.parquimetro2adjt.domain.entity.Estacionamento;
import br.com.parquimetro2adjt.infra.repository.EstacionamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class FinalizarEstacionamentoFixoService {
    private static final String ZONE_ID_SAO_PAULO = "America/Sao_Paulo";
    private final Logger logger = LoggerFactory.getLogger(FinalizarEstacionamentoFixoService.class);

    private final EstacionamentoService estacionamentoService;
    private final EstacionamentoRepository estacionamentoRepository;

    public FinalizarEstacionamentoFixoService(EstacionamentoService estacionamentoService, EstacionamentoRepository estacionamentoRepository) {
        this.estacionamentoService = estacionamentoService;
        this.estacionamentoRepository = estacionamentoRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void finalizarEstacionamento() {
        List<Estacionamento> registrosSemHorarioSaida = estacionamentoRepository.buscarEstacionamentosAtivosPeriodoFixoNaoNotificados();

        registrosSemHorarioSaida.forEach(registro -> {
            ZonedDateTime dataInicial = registro.getHoraInicial()
                    .plusHours(registro.getDuracaoDesejada()).atZone(ZoneId.of(ZONE_ID_SAO_PAULO));

            ZonedDateTime dataAtual = LocalDateTime.now().atZone(ZoneId.of(ZONE_ID_SAO_PAULO));

            if(dataInicial.isAfter(dataAtual)) {
                logger.info("Finalizando periodo de estacionamento do veiculo com a placa '{}'",
                        registro.getVeiculo().getPlaca());

                estacionamentoService.finalizar(registro.getVeiculo().getPlaca());
            }
        });
    }
}
