package br.com.parquimetro2adjt.domain.service;

import br.com.parquimetro2adjt.application.controller.exceptions.NaoEncontradoException;
import br.com.parquimetro2adjt.application.controller.exceptions.VeiculoNaoPertenceAoCondutorException;
import br.com.parquimetro2adjt.application.request.VeiculoRequestDTO;
import br.com.parquimetro2adjt.application.response.VeiculoResponseDTO;
import br.com.parquimetro2adjt.domain.entity.Condutor;
import br.com.parquimetro2adjt.domain.entity.Veiculo;
import br.com.parquimetro2adjt.infra.repository.VeiculoRepository;
import br.com.parquimetro2adjt.utils.Utils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class VeiculoService {
    private final VeiculoRepository veiculoRepository;
    private final CondutorService condutorService;
    private final Utils utils;

    public VeiculoService(VeiculoRepository veiculoRepository, CondutorService condutorService, Utils utils) {
        this.veiculoRepository = veiculoRepository;
        this.condutorService = condutorService;
        this.utils = utils;
    }

    public Page<VeiculoResponseDTO> findAll(Pageable pageable,
                                            String placa,
                                            String modelo,
                                            String cor,
                                            String marca) {
        Example<Veiculo> example = Example.of(Veiculo.builder()
                .placa(placa)
                .modelo(modelo)
                .cor(cor)
                .build()
        );

        Page<Veiculo> veiculos = veiculoRepository.findAll(example, pageable);
        return veiculos.map(this::toResponseDTO);
    }

    public VeiculoResponseDTO cadastrar(VeiculoRequestDTO veiculoRequestDTO, String idCondutor) {
        Veiculo veiculo = toEntity(veiculoRequestDTO);
        veiculo.setCondutor(condutorService.buscaPorId(idCondutor));

        return toResponseDTO(veiculoRepository.save(veiculo));
    }

    public VeiculoResponseDTO atualizar(String idCondutor, String idVeiculo, VeiculoRequestDTO veiculoRequestDTO) {
        Condutor condutor = condutorService.buscaPorId(idCondutor);
        Veiculo request = toEntity(veiculoRequestDTO);

        Veiculo veiculo = buscarPorId(idVeiculo);

        if (!veiculoPertenceAoCondutor(veiculo, condutor))
            throw new VeiculoNaoPertenceAoCondutorException(
                    String.format("O veiculo com o id '%d' não pertence ao condutor com id '%d'!",
                            idVeiculo, idCondutor));

        utils.copyNonNullProperties(request, veiculo);

        return toResponseDTO(veiculoRepository.save(veiculo));
    }

    public void deletar(String idCondutor, String idVeiculo) {
        condutorService.existePorId(idCondutor);
        existePorId(idVeiculo);

        if (!veiculoPertenceAoCondutor(idVeiculo, idCondutor))
            throw new VeiculoNaoPertenceAoCondutorException(
                String.format("O veiculo com o id '%d' não pertence ao condutor com id '%d'!",
                        idVeiculo, idCondutor));

        veiculoRepository.deleteById(idVeiculo);

    }

    public Veiculo buscarPorId(String idVeiculo) {
        return veiculoRepository.findById(idVeiculo)
                .orElseThrow(() -> new NaoEncontradoException(
                        String.format("Veiculo com o id '%d' não encontrado", idVeiculo)));
    }

    public Page<VeiculoResponseDTO> consultarVeiculosPorCondutor(String idCondutor, Pageable pageable) {
        condutorService.existePorId(idCondutor);
        Page<Veiculo> veiculos = veiculoRepository.findAllByCondutorId(idCondutor, pageable);

        return veiculos.map(this::toResponseDTO);
    }

    public void existePorId(String idVeiculo) {
        if (!veiculoRepository.existsById(idVeiculo))
            throw new NaoEncontradoException(
                    String.format("Veiculo com o id '%d' não encontrado", idVeiculo));
    }

    public boolean veiculoPertenceAoCondutor(Veiculo veiculo, Condutor condutor) {
        return ObjectUtils.nullSafeEquals(veiculo.getCondutor().getId(), condutor.getId());
    }

    public boolean veiculoPertenceAoCondutor(String veiculoId, String condutorId) {
        return veiculoRepository.existsVeiculoByIdAndCondutorId(veiculoId, condutorId);
    }

    public VeiculoResponseDTO toResponseDTO(Veiculo veiculo) {
        return new VeiculoResponseDTO(
                veiculo.getId(),
                veiculo.getModelo(),
                veiculo.getPlaca(),
                veiculo.getMarca(),
                veiculo.getCor(),
                isEmpty(veiculo.getCondutor()) ? null : condutorService.toResponseDto(veiculo.getCondutor())
        );
    }

    public Veiculo toEntity(VeiculoRequestDTO veiculoRequestDTO) {
        return Veiculo.builder()
                .id(veiculoRequestDTO.id())
                .marca(veiculoRequestDTO.marca())
                .modelo(veiculoRequestDTO.modelo())
                .cor(veiculoRequestDTO.cor())
                .placa(veiculoRequestDTO.placa())
                .condutor(isEmpty(veiculoRequestDTO.condutorRequestDTO()) ? null : condutorService.toEntity(veiculoRequestDTO.condutorRequestDTO()))
                .build();
    }
}
