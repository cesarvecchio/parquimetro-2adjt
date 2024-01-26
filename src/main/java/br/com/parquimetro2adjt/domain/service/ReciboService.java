package br.com.parquimetro2adjt.domain.service;

import br.com.parquimetro2adjt.domain.entity.Estacionamento;
import br.com.parquimetro2adjt.infra.repository.EstacionamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReciboService {
    private final EstacionamentoRepository estacionamentoRepository;

    public ReciboService(EstacionamentoRepository estacionamentoRepository) {
        this.estacionamentoRepository = estacionamentoRepository;
    }

    public String gerarRecibo(String placa){
        List<Estacionamento> lista = estacionamentoRepository.buscarEstacionamentoFinalizadoPorPlaca(placa);

        if (!lista.isEmpty()) {
            Estacionamento estacionamento = lista.get(lista.size()-1);

            return String.format(
                    """
                    | Recibo do Estacionamento
                    | ----------------------------------------
                    | Nome: %s
                    | Email: %s
                    |
                    | ----------------------------------------
                    | Placa do Veiculo: %s
                    | Tipo do Estacionamento: %s
                    | Duração Desejada: %d horas
                    | Hora Inicial: %s
                    | Hora Final: %s
                    |
                    | ----------------------------------------
                    | Valor: R$ %.2f
                    |
                    | ----------------------------------------
                    | Horario da Emissão do Recibo: %s
                    | ----------------------------------------
                    
                   """,
                    estacionamento.getNome(),
                    estacionamento.getEmail(),
                    placa,
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
