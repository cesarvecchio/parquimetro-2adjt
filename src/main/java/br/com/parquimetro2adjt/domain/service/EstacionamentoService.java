package br.com.parquimetro2adjt.domain.service;

import br.com.parquimetro2adjt.application.controller.exceptions.CadastradoException;
import br.com.parquimetro2adjt.application.controller.exceptions.CampoVazioException;
import br.com.parquimetro2adjt.application.controller.exceptions.NaoEncontradoException;
import br.com.parquimetro2adjt.application.controller.exceptions.TipoPeriodoEPagamentoDivergentesException;
import br.com.parquimetro2adjt.application.request.EstacionamentoRequestDTO;
import br.com.parquimetro2adjt.application.response.EstacionamentoResponseDTO;
import br.com.parquimetro2adjt.domain.entity.Condutor;
import br.com.parquimetro2adjt.domain.entity.Estacionamento;
import br.com.parquimetro2adjt.domain.enums.PagamentoEnum;
import br.com.parquimetro2adjt.domain.enums.TipoEstacionamentoEnum;
import br.com.parquimetro2adjt.infra.repository.CondutorRepository;
import br.com.parquimetro2adjt.infra.repository.EstacionamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EstacionamentoService {
    private final Logger logger = LoggerFactory.getLogger(EstacionamentoService.class);
    private final EstacionamentoRepository estacionamentoRepository;
    private final CondutorRepository condutorRepository;
    private final PagamentoService pagamentoService;

    public EstacionamentoService(EstacionamentoRepository estacionamentoRepository, CondutorRepository condutorRepository,
                                 PagamentoService pagamentoService) {
        this.estacionamentoRepository = estacionamentoRepository;
        this.condutorRepository = condutorRepository;
        this.pagamentoService = pagamentoService;
    }

    public EstacionamentoResponseDTO create(String placa, EstacionamentoRequestDTO requestDTO) {
        logger.info("Iniciando registro de estacionamento para o veiculo com a placa '{}'", placa);

        Optional<Condutor> condutorOpt = condutorRepository.findVeiculoPorPlaca(placa);

        if (condutorOpt.isEmpty()) {
            logger.warn("Veiculo com a placa '{}' não foi encontrado!", placa);

            throw new NaoEncontradoException(
                    String.format("O veiculo com a placa '%s' não foi encontrado!", placa));
        }

        logger.info("Validando se o veiculo possui registro de estacionamento ativo");
        Optional<Estacionamento> estacionamentoOpt = estacionamentoRepository.buscarEstacionamentoAtivoPorPlaca(placa);

        if (estacionamentoOpt.isPresent()) {
            logger.warn("Veiculo com a placa '{}' possui um registro de estacionamento ativo!", placa);
            throw new CadastradoException(
                    String.format("O veiculo com a placa '%s' já possui um registro de estacionamento ativo!", placa));
        }

        validarPeriodoFixo(requestDTO);

        Condutor condutor = condutorOpt.get();

        validarPeriodoEPagamento(requestDTO.tipoEstacionamento(), condutor.getFormaPagamento());

        Estacionamento estacionamento = toEntity(requestDTO);
        estacionamento.setHoraInicial(LocalDateTime.now());
        estacionamento.setVeiculo(condutor.getVeiculos().stream()
                .filter(veiculo -> veiculo.getPlaca().equals(placa)).findFirst().get());
        estacionamento.setNome(condutor.getNome());
        estacionamento.setEmail(condutor.getEmail());

        logger.info("Finalizando registro de estacionamento para o veiculo com a placa '{}'", placa);
        return toResponse(estacionamentoRepository.save(estacionamento));
    }

    public void validarPeriodoEPagamento(TipoEstacionamentoEnum tipoEstacionamentoEnum, PagamentoEnum pagamentoEnum) {
        logger.info("Validando forma de pagamento!");

        if (TipoEstacionamentoEnum.VARIAVEL.equals(tipoEstacionamentoEnum)
                && PagamentoEnum.PIX.equals(pagamentoEnum)) {
            throw new TipoPeriodoEPagamentoDivergentesException(
                    "A opção PIX só está disponível para períodos de estacionamento fixos!");
        }
    }

    public void validarPeriodoFixo(EstacionamentoRequestDTO requestDTO) {
        logger.info("Validando periodo de estacionamento!");

        if (TipoEstacionamentoEnum.FIXO.equals(requestDTO.tipoEstacionamento())
                && ObjectUtils.isEmpty(requestDTO.duracaoDesejada())) {
            throw new CampoVazioException(
                    String.format("Para periodos '%s' o sistema requer que o campo '%s'! seja informado",
                            requestDTO.tipoEstacionamento(), "duracaoDesejada"));
        }
    }

    public List<EstacionamentoResponseDTO> buscarTodosEstacionamentoPorPlaca(String placa) {
        List<Estacionamento> estacionamentoList = estacionamentoRepository.buscarTodosEstacionamentoPorPlaca(placa);

        if (estacionamentoList.isEmpty()) {
            throw new NaoEncontradoException("Este veiculo não possui nenhum registro de estacionamento!");
        }

        return estacionamentoList.stream().map(this::toResponse).toList();
    }

    public EstacionamentoResponseDTO buscarEstacionamentoAtivoPorPlaca(String placa) {
        Optional<Estacionamento> estacionamentoOpt = estacionamentoRepository.buscarEstacionamentoAtivoPorPlaca(placa);

        return toResponse(estacionamentoOpt.orElseThrow(() -> new NaoEncontradoException(
                String.format("O Veiculo com a placa '%s' não possui nenhum registro de estacionamento ativo!",
                        placa))));
    }

    public EstacionamentoResponseDTO finalizar(String placa) {
        Optional<Estacionamento> estacionamentoOpt = estacionamentoRepository.buscarEstacionamentoAtivoPorPlaca(placa);

        if (estacionamentoOpt.isEmpty()) {
            throw new NaoEncontradoException(
                    String.format("O Veiculo com a placa '%s' não possui nenhum registro de estacionamento ativo!",
                            placa));
        }

        Estacionamento estacionamento = estacionamentoOpt.get();
        estacionamento.setHoraFinal(LocalDateTime.now());
        estacionamento.setValorCobrado(pagamentoService.calcularValorPagamento(estacionamento));

        return toResponse(estacionamentoRepository.save(estacionamento));
    }

    public List<EstacionamentoResponseDTO> buscarEstacionamentoFinalizadoPorPlaca(String placa) {
        List<Estacionamento> estacionamentoList = estacionamentoRepository
                .buscarEstacionamentoFinalizadoPorPlaca(placa);

        if(estacionamentoList.isEmpty()){
            throw new NaoEncontradoException(
                    String.format("O Veiculo com a placa '%s' não possui nenhum registro de estacionamento finalizado!",
                            placa));
        }

        return estacionamentoList.stream().map(this::toResponse).toList();
    }

    public List<EstacionamentoResponseDTO> buscarEstacionamentosAtivos() {
        List<Estacionamento> estacionamentoList = estacionamentoRepository.buscarEstacionamentosAtivos();

        if (estacionamentoList.isEmpty()) {
            throw new NaoEncontradoException("Não possui nenhum veliculo com registro de estacionamento ativo");
        }

        return estacionamentoList.stream().map(this::toResponse).toList();
    }

    public Estacionamento toEntity(EstacionamentoRequestDTO requestDTO) {
        Estacionamento estacionamento = new Estacionamento();

        estacionamento.setTipoEstacionamento(requestDTO.tipoEstacionamento());
        estacionamento.setDuracaoDesejada(requestDTO.duracaoDesejada());

        return estacionamento;
    }

    public EstacionamentoResponseDTO toResponse(Estacionamento estacionamento) {
        return new EstacionamentoResponseDTO(
                estacionamento.getId(),
                estacionamento.getTipoEstacionamento(),
                estacionamento.getDuracaoDesejada(),
                estacionamento.getHoraInicial(),
                estacionamento.getHoraFinal(),
                estacionamento.getVeiculo(),
                estacionamento.getNome(),
                estacionamento.getEmail(),
                estacionamento.getDataHoraUltimoAlerta(),
                estacionamento.getValorCobrado()
        );
    }

}
