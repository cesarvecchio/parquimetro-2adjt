package br.com.parquimetro2adjt.domain.service;

import br.com.parquimetro2adjt.domain.entity.Estacionamento;
import br.com.parquimetro2adjt.infra.repository.EstacionamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Service
public class AlertaService {

    Logger logger = LoggerFactory.getLogger(AlertaService.class);

    private final EstacionamentoRepository estacionamentoRepository;
    private final JavaMailSender mailSender;

    private static final String ZONE_ID_SAO_PAULO = "America/Sao_Paulo";

    @Value("${quantidade.threads}")
    private int qtThreads;

    @Value("${spring.mail.username}")
    private String from;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    private ExecutorService executorService() {
        return Executors.newFixedThreadPool(qtThreads);
    }

    public AlertaService(EstacionamentoRepository estacionamentoRepository, JavaMailSender mailSender) {
        this.estacionamentoRepository = estacionamentoRepository;
        this.mailSender = mailSender;
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
            executorService().execute(() -> enviaEmailPeriodoFixo(registro, minutosAntesDeFecharHoraLimiteParaAlertar, dataAtual, formatter, registrosAlertados));
        }
        shutdownExecutor();
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
            executorService().execute(() -> enviaEmailPeriodoVariavel(registro, minutosAntesDeFecharHoraLimiteParaAlertar, dataAtual, formatter, registrosAlertados));
        }
        shutdownExecutor();
        estacionamentoRepository.saveAll(registrosAlertados);
    }

    private void enviaEmailPeriodoFixo(Estacionamento registro, long minutosAntesDeFecharHoraLimiteParaAlertar, ZonedDateTime dataAtual, DateTimeFormatter formatter, List<Estacionamento> registrosAlertados) {
        ZonedDateTime dataLimite = registro.getHoraInicial()
                .atZone(ZoneId.of(ZONE_ID_SAO_PAULO))
                .plusHours(registro.getDuracaoDesejada());
        ZonedDateTime dataInicioAlerta = dataLimite.minusMinutes(minutosAntesDeFecharHoraLimiteParaAlertar);
        if (dataAtual.isAfter(dataInicioAlerta) && dataAtual.isBefore(dataLimite)) {

            String logMsgEmail = String.format("**** ENVIANDO E-MAIL PARA ALERTAR SOBRE TEMPO RESTANTE DE ESTACIONAMENTO PERÍODO FIXO ****%n");
            logMsgEmail += String.format("Destinatário: %s%n", registro.getEmail());
            String assunto = "Tempo Restante Estacionamento";
            logMsgEmail += String.format("Assunto: %s%n", assunto);
            String corpoEmail = String.format("Caro(a) %s, o seu veículo de placa %s tem aproximadamente %d minutos restantes para " +
                            "permanecer estacionado!! Favor registrar saída até a data limite de: %s.%n",
                    registro.getNome(),
                    registro.getVeiculo().getPlaca(),
                    minutosAntesDeFecharHoraLimiteParaAlertar,
                    dataLimite.format(formatter));
            logMsgEmail += String.format("Corpo: %s", corpoEmail);

            registro.setDataHoraUltimoAlerta(LocalDateTime.now());
            registrosAlertados.add(registro);

            enviarEmail(registro.getEmail(), assunto, corpoEmail);

            logger.info(logMsgEmail);
        }
    }

    private void enviaEmailPeriodoVariavel(Estacionamento registro, long minutosAntesDeFecharHoraLimiteParaAlertar, ZonedDateTime dataAtual, DateTimeFormatter formatter, List<Estacionamento> registrosAlertados) {
        ZonedDateTime dataLimite = getDataLimite(registro);
        ZonedDateTime dataInicioAlerta = dataLimite.minusMinutes(minutosAntesDeFecharHoraLimiteParaAlertar);
        if (dataAtual.isAfter(dataInicioAlerta) && dataAtual.isBefore(dataLimite)) {

            String logMsgEmail = String.format("**** ENVIANDO E-MAIL PARA ALERTAR SOBRE TEMPO RESTANTE DE ESTACIONAMENTO PERÍODO VARIÁVEL ****%n");
            logMsgEmail += String.format("Destinatário: %s%n", registro.getEmail());
            String assunto = "Tempo Restante Estacionamento";
            logMsgEmail += String.format("Assunto: %s%n", assunto);
            String corpoEmail = String.format("Corpo: Caro(a) %s, o seu veículo de placa %s tem aproximadamente %d minutos restantes para " +
                            "permanecer estacionado!! Caso não registre saída até a data limite de: %s. será incrementado automaticamente " +
                            "1 hora no período estacionado!%n",
                    registro.getNome(),
                    registro.getVeiculo().getPlaca(),
                    minutosAntesDeFecharHoraLimiteParaAlertar,
                    dataLimite.format(formatter));
            logMsgEmail += String.format("Corpo: %s%n", corpoEmail);

            registro.setDataHoraUltimoAlerta(LocalDateTime.now());
            registrosAlertados.add(registro);

            enviarEmail(registro.getEmail(), assunto, corpoEmail);

            logger.info(logMsgEmail);
        }
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

    private void shutdownExecutor() {
        try {
            if (!executorService().awaitTermination(5, TimeUnit.SECONDS)) {
                executorService().shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService().shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private void enviarEmail(String email, String assunto, String corpoEmail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(assunto);
        simpleMailMessage.setText(corpoEmail);
        mailSender.send(simpleMailMessage);
    }
}
