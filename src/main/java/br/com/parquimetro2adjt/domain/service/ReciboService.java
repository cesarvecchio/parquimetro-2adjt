package br.com.parquimetro2adjt.domain.service;

import br.com.parquimetro2adjt.domain.entity.Estacionamento;
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

        if (optional.isPresent()) {
            Estacionamento estacionamento = optional.get();

            return String.format(
                    "Recibo do Estacionamento \n"
                            + "------------------------ \n"
                            + "Tipo do Estacionamento: %s \n"
                            + "Duração Desejada: %d \n"
                            + "Hora Inicial: %s \n"
                            + "Hora Final: %s\n "
                            + "Forma de Pagamento: %s\n "
                            + "Valor: %s\n "
                            + "Hora Final: %s\n "
                            + "------------------------ ",
                    estacionamento.getTipoEstacionamento(),
                    estacionamento.getDuracaoDesejada(),
                    estacionamento.getHoraInicial(),
                    estacionamento.getHoraFinal(),
                    estacionamento.getValorCobrado(),
                    LocalDateTime.now()
            );
        } else {
            return "Nenhum estacionamento finalizado encontrado para a placa: " + placa;
        }
    }
}
