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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class AlertaService {

    Logger logger = LoggerFactory.getLogger(AlertaService.class);
    EstacionamentoRepository estacionamentoRepository;

    private static final String ZONE_ID_SAO_PAULO = "America/Sao_Paulo";

    public AlertaService(EstacionamentoRepository estacionamentoRepository) {
        this.estacionamentoRepository = estacionamentoRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void enviarAlertaPeriodoFixo() {
        logger.info("**** EXECUTOU SCHEDULED DE PERÍODO FIXO ****");
        ZonedDateTime dataAtual = LocalDateTime.now().atZone(ZoneId.of(ZONE_ID_SAO_PAULO));
        long minutosAntesDeFecharHoraLimiteParaAlertar = 15;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy 'às' HH:mm:ss");

        List<Estacionamento> registrosSemHorarioSaida = estacionamentoRepository.buscarEstacionamentosAtivosPeriodoFixoNaoNotificados();
        List<Estacionamento> registrosAlertados = new ArrayList<>();

        for (Estacionamento registro : registrosSemHorarioSaida) {
            ZonedDateTime dataLimite = registro.getHoraInicial()
                    .atZone(ZoneId.of(ZONE_ID_SAO_PAULO))
                    .plusHours(registro.getDuracaoDesejada());
            ZonedDateTime dataInicioAlerta = dataLimite.minusMinutes(minutosAntesDeFecharHoraLimiteParaAlertar);
            if (dataAtual.isAfter(dataInicioAlerta) && dataAtual.isBefore(dataLimite)) {
                String msgEmail = String.format("**** ENVIANDO E-MAIL PARA ALERTAR SOBRE TEMPO RESTANTE DE ESTACIONAMENTO PERÍODO FIXO ****%n");
                msgEmail += String.format("Destinatário: %s%n", registro.getEmail());
                msgEmail += String.format("Assunto: Tempo Restante Estacionamento%n");
                msgEmail += String.format("Corpo: Caro(a) %s, o seu veículo de placa %s tem aproximadamente %d minutos restantes para " +
                                "permanecer estacionado!! Favor registrar saída até a data limite de: %s.%n",
                        registro.getNome(),
                        registro.getVeiculo().getPlaca(),
                        minutosAntesDeFecharHoraLimiteParaAlertar,
                        dataLimite.format(formatter));
                registro.setDataHoraUltimoAlerta(LocalDateTime.now());
                registrosAlertados.add(registro);
                logger.info(msgEmail);
            }
        }
        estacionamentoRepository.saveAll(registrosAlertados);
    }

    @Scheduled(cron = "0 * * * * *")
    public void enviarAlertaPeriodoVariavel() {
        logger.info("**** EXECUTOU SCHEDULED DE PERÍODO VARIÁVEL ****");
        ZonedDateTime dataAtual = LocalDateTime.now().atZone(ZoneId.of(ZONE_ID_SAO_PAULO));
        long minutosAntesDeFecharHoraLimiteParaAlertar = 15;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy 'às' HH:mm:ss");

        List<Estacionamento> registrosSemHorarioSaida = estacionamentoRepository.buscarEstacionamentosAtivosPeriodoVariavel();
        List<Estacionamento> registrosAlertados = new ArrayList<>();

        for (Estacionamento registro : registrosSemHorarioSaida) {
            ZonedDateTime dataLimite = getDataLimite(registro);
            ZonedDateTime dataInicioAlerta = dataLimite.minusMinutes(minutosAntesDeFecharHoraLimiteParaAlertar);
            if (dataAtual.isAfter(dataInicioAlerta) && dataAtual.isBefore(dataLimite)) {
                String msgEmail = String.format("**** ENVIANDO E-MAIL PARA ALERTAR SOBRE TEMPO RESTANTE DE ESTACIONAMENTO PERÍODO VARIÁVEL ****%n");
                msgEmail += String.format("Destinatário: %s%n", registro.getEmail());
                msgEmail += String.format("Assunto: Tempo Restante Estacionamento%n");
                msgEmail += String.format("Corpo: Caro(a) %s, o seu veículo de placa %s tem aproximadamente %d minutos restantes para " +
                                "permanecer estacionado!! Caso não registre saída até a data limite de: %s. será incrementado automaticamente " +
                                "1 hora no período estacionado!%n",
                        registro.getNome(),
                        registro.getVeiculo().getPlaca(),
                        minutosAntesDeFecharHoraLimiteParaAlertar,
                        dataLimite.format(formatter));
                registro.setDataHoraUltimoAlerta(LocalDateTime.now());
                registrosAlertados.add(registro);
                logger.info(msgEmail);
            }
        }
        estacionamentoRepository.saveAll(registrosAlertados);
    }

    private static ZonedDateTime getDataLimite(Estacionamento registro) {
        return Objects.isNull(registro.getDataHoraUltimoAlerta())
                ? registro.getHoraInicial()
                    .atZone(ZoneId.of(ZONE_ID_SAO_PAULO))
                    .plusHours(1)
                : registro.getDataHoraUltimoAlerta()
                    .atZone(ZoneId.of(ZONE_ID_SAO_PAULO))
                .plusHours(1);
    }
}
