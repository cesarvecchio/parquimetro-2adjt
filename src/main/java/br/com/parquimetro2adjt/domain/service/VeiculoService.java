package br.com.parquimetro2adjt.domain.service;

import br.com.parquimetro2adjt.application.controller.exceptions.CadastradoException;
import br.com.parquimetro2adjt.application.controller.exceptions.NaoEncontradoException;
import br.com.parquimetro2adjt.application.request.VeiculoRequestDTO;
import br.com.parquimetro2adjt.application.response.VeiculoResponseDTO;
import br.com.parquimetro2adjt.domain.entity.Condutor;
import br.com.parquimetro2adjt.domain.valueObject.Veiculo;
import br.com.parquimetro2adjt.infra.repository.CondutorRepository;
import br.com.parquimetro2adjt.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {
    private static final String VEICULO_NAO_ENCONTRADO = "O veiculo com a placa '%s' não foi encontrado!";
    private final Logger logger = LoggerFactory.getLogger(VeiculoService.class);
    private final CondutorService condutorService;
    private final CondutorRepository condutorRepository;
    private final Utils utils;

    public VeiculoService(CondutorService condutorService, CondutorRepository condutorRepository, Utils utils) {
        this.condutorService = condutorService;
        this.condutorRepository = condutorRepository;
        this.utils = utils;
    }

    public VeiculoResponseDTO buscar(String placa){
        Optional<Condutor> condutorOpt = condutorRepository.findVeiculoPorPlaca(placa);

        if(condutorOpt.isEmpty()) {
            throw new NaoEncontradoException(
                    String.format(VEICULO_NAO_ENCONTRADO, placa));
        }
        Condutor condutor = condutorOpt.get();

        return toResponseDTO(condutor.getVeiculos().stream()
                .filter(veiculo -> veiculo.getPlaca().equals(placa)).findFirst().get());
    }

    public List<VeiculoResponseDTO> findAllByCondutor(String id){
        Condutor condutor = condutorService.buscaPorId(id);

        if(ObjectUtils.isEmpty(condutor.getVeiculos()) || condutor.getVeiculos().isEmpty()) {
            throw new NaoEncontradoException(
                    String.format("O Condutor com id '%s' não possui veiculos cadatrados!", id));
        }

        return condutor.getVeiculos().stream().map(this::toResponseDTO).toList();
    }

    public VeiculoResponseDTO cadastrar(String idCondutor, VeiculoRequestDTO requestDTO) {

        if(condutorRepository.findVeiculoPorPlaca(requestDTO.placa()).isPresent()){
            throw new CadastradoException(String.format(
                    "O veiculo com a placa '%s' já está cadastrado no sistema!",
                    requestDTO.placa()));
        }

        Condutor condutor = this.condutorService.buscaPorId(idCondutor);

        List<Veiculo> listaVeiculos = new ArrayList<>(ObjectUtils.isEmpty(condutor.getVeiculos())
                ? new ArrayList<>() : condutor.getVeiculos());
        listaVeiculos.add(toEntity(requestDTO));

        condutor.setVeiculos(listaVeiculos);

        condutor = condutorRepository.save(condutor);

        return toResponseDTO(condutor.getVeiculos().stream()
                .filter(veiculo -> veiculo.getPlaca().equals(requestDTO.placa()))
                .findFirst().get());
    }

    public VeiculoResponseDTO atualizar(String placa, VeiculoRequestDTO requestDTO) {
        Optional<Condutor> condutorOpt = condutorRepository.findVeiculoPorPlaca(placa);

        if(condutorOpt.isEmpty()) {
            throw new NaoEncontradoException(
                    String.format(VEICULO_NAO_ENCONTRADO, placa));
        }

        Condutor condutor = condutorOpt.get();

        condutor.getVeiculos().forEach(veiculo -> {
            if(veiculo.getPlaca().equals(placa)){
                utils.copyNonNullProperties(requestDTO, veiculo);
                return;
            }
        });

        condutor = condutorRepository.save(condutor);

        return toResponseDTO(condutor.getVeiculos().stream()
                .filter(veiculo -> veiculo.getPlaca().equals(placa))
                .findFirst().get());
    }

    public void deletar(String placa) {
        Optional<Condutor> condutorOpt = condutorRepository.findVeiculoPorPlaca(placa);

        if(condutorOpt.isEmpty()) {
            throw new NaoEncontradoException(
                    String.format(VEICULO_NAO_ENCONTRADO, placa));
        }
        Condutor condutor = condutorOpt.get();

        condutor.getVeiculos().removeIf(veiculo -> veiculo.getPlaca().equals(placa));

        condutorRepository.save(condutor);
    }

    public VeiculoResponseDTO toResponseDTO(Veiculo veiculo) {
        return new VeiculoResponseDTO(
                veiculo.getModelo(),
                veiculo.getPlaca(),
                veiculo.getMarca(),
                veiculo.getCor()
        );
    }

    public Veiculo toEntity(VeiculoRequestDTO veiculoRequestDTO) {
        return Veiculo.builder()
                .marca(veiculoRequestDTO.marca())
                .modelo(veiculoRequestDTO.modelo())
                .cor(veiculoRequestDTO.cor())
                .placa(veiculoRequestDTO.placa())
                .build();
    }
}
