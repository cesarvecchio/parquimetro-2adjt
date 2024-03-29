package br.com.parquimetro2adjt.domain.service;

import br.com.parquimetro2adjt.application.controller.exceptions.CpfException;
import br.com.parquimetro2adjt.application.controller.exceptions.NaoEncontradoException;
import br.com.parquimetro2adjt.application.request.CondutorRequestDTO;
import br.com.parquimetro2adjt.application.response.CondutorResponseDTO;
import br.com.parquimetro2adjt.domain.entity.Condutor;
import br.com.parquimetro2adjt.domain.valueObject.Veiculo;
import br.com.parquimetro2adjt.domain.enums.PagamentoEnum;
import br.com.parquimetro2adjt.domain.valueObject.Endereco;
import br.com.parquimetro2adjt.infra.repository.CondutorRepository;
import br.com.parquimetro2adjt.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CondutorService {
    private final Logger logger = LoggerFactory.getLogger(CondutorService.class);
    private final CondutorRepository condutorRepository;
    private final Utils utils;

    public CondutorService(CondutorRepository condutorRepository, Utils utils) {
        this.condutorRepository = condutorRepository;
        this.utils = utils;
    }

    public CondutorResponseDTO cadastrar(CondutorRequestDTO condutorRequestDTO) {
        logger.info("Iniciando cadastro do condutor!");

        Condutor condutor = toEntity(condutorRequestDTO);

        if (cpfExistente(condutorRequestDTO.cpf()))
            throw new CpfException("Esse cpf já está sendo utilizado");

        logger.info("Finalizando cadastro do condutor!");
        return toResponseDto(condutorRepository.save(condutor));
    }

    public CondutorResponseDTO atualizar(String id, CondutorRequestDTO condutorRequestDTO) {
        Condutor condutor = buscaPorId(id);

        utils.copyNonNullProperties(condutorRequestDTO, condutor);

        return toResponseDto(condutorRepository.save(condutor));
    }

    public void deletar(String id) {
        existePorId(id);

        condutorRepository.deleteById(id);
    }

    public Condutor buscaPorId(String idCondutor) {
        return condutorRepository.findById(idCondutor)
                .orElseThrow(() -> new NaoEncontradoException(
                        String.format("Condutor com o id '%s' não encontrado", idCondutor)));
    }

    public Page<CondutorResponseDTO> findAll(Pageable pageable,
                                            String nome,
                                            String email,
                                            String cpf,
                                            PagamentoEnum formaPagamento,
                                            Endereco endereco,
                                            List<Veiculo> veiculos) {
        Example<Condutor> example = Example.of(Condutor.builder()
                .nome(nome)
                .email(email)
                .cpf(cpf)
                .formaPagamento(formaPagamento)
                .endereco(endereco)
                .veiculos(veiculos)
                .build()
        );

        Page<Condutor> condutor = condutorRepository.findAll(example, pageable);
        return condutor.map(this::toResponseDto);
    }

    private boolean cpfExistente(String cpf) {
        return condutorRepository.existsByCpf(cpf);
    }

    public void existePorId(String idCondutor) {
        if (!condutorRepository.existsById(idCondutor))
            throw new NaoEncontradoException(
                    String.format("Condutor com o id '%s' não encontrado", idCondutor));

    }

    public CondutorResponseDTO cadastarVeiculo(Condutor condutor, Veiculo veiculo){
        condutor.getVeiculos().add(veiculo);
        return toResponseDto(condutorRepository.save(condutor));
    }

    public CondutorResponseDTO toResponseDto(Condutor condutor) {
        return new CondutorResponseDTO(
                condutor.getId(),
                condutor.getNome(),
                condutor.getCpf(),
                condutor.getEmail(),
                condutor.getFormaPagamento(),
                condutor.getEndereco(),
                condutor.getVeiculos()
        );
    }

    public Condutor toEntity(CondutorRequestDTO condutorRequestDTO) {
        return Condutor.builder()
                .nome(condutorRequestDTO.nome())
                .cpf(condutorRequestDTO.cpf())
                .email(condutorRequestDTO.email())
                .senha(condutorRequestDTO.senha())
                .endereco(condutorRequestDTO.endereco())
                .build();
    }


}
