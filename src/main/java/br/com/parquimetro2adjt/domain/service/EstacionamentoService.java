package br.com.parquimetro2adjt.domain.service;

import br.com.parquimetro2adjt.application.request.EstacionamentoRequestDTO;
import br.com.parquimetro2adjt.application.response.EstacionamentoResponseDTO;
import br.com.parquimetro2adjt.domain.entity.Condutor;
import br.com.parquimetro2adjt.domain.entity.Estacionamento;
import br.com.parquimetro2adjt.domain.entity.Veiculo;
import br.com.parquimetro2adjt.domain.enums.PagamentoEnum;
import br.com.parquimetro2adjt.domain.enums.TipoEstacionamentoEnum;
import br.com.parquimetro2adjt.infra.repository.CondutorRepository;
import br.com.parquimetro2adjt.infra.repository.EstacionamentoRepository;
import org.springframework.stereotype.Service;

@Service
public class EstacionamentoService {
    private EstacionamentoRepository estacionamentoRepository;
    private CondutorService condutorService;
    private CondutorRepository condutorRepository;//TODO: mudar para service
    private VeiculoService veiculoService;

    public EstacionamentoResponseDTO create(String idCondutor, String placa, EstacionamentoRequestDTO requestDTO) {
        Veiculo veiculo = null /*veiculoService.buscarPelaPlaca(placa)*/;

        Condutor condutor = condutorRepository.findById(idCondutor)
                .orElseThrow(() -> new RuntimeException("Condutor não existe"));//TODO: mudar para exceção expecifica

        if(condutor.getVeiculos().isEmpty())
            throw new RuntimeException("Condutor não possui nenhum carro registrado!");//TODO: mudar para exceção expecifica

        validarCondutorVeiculo(condutor, veiculo);

        if(condutor.getFormaPagamento().equals(PagamentoEnum.PIX)
        && !requestDTO.tipoEstacionamento().equals(TipoEstacionamentoEnum.FIXO))
            throw new RuntimeException("A opção PIX só está disponível para períodos de estacionamento fixos!");//TODO: mudar para exceção expecifica


        return null;
    }

    public EstacionamentoResponseDTO finalizarEstacionamento(){
        return null;
    }

    public void validarCondutorVeiculo(Condutor condutor, Veiculo veiculo) {
        Boolean dono = false;

        for(Veiculo v: condutor.getVeiculos()){
            if(v.getPlaca().equals(veiculo.getPlaca())){
                dono = true;
                break;
            }
        }

        if(!dono)
            throw new RuntimeException("O condutor não é dono desse veiculo!");//TODO: mudar para exceção expecifica
    }

    public Estacionamento toEstacionamento(EstacionamentoRequestDTO requestDTO) {
        Estacionamento estacionamento = new Estacionamento();

        estacionamento.setTipoEstacionamento(requestDTO.tipoEstacionamento());
        estacionamento.setDuracaoDesejada(requestDTO.duracaoDesejada());
        estacionamento.setHoraInicial(requestDTO.horaInicial());

        return estacionamento;
    }

    public EstacionamentoResponseDTO toResponse(Estacionamento estacionamento) {
        return new EstacionamentoResponseDTO(
                estacionamento.getId().toString(),
                estacionamento.getTipoEstacionamento(),
                estacionamento.getDuracaoDesejada(),
                estacionamento.getHoraInicial(),
                estacionamento.getHoraFinal(),
                estacionamento.getVeiculo()
        );
    }
}
