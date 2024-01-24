package br.com.parquimetro2adjt.domain.service;

import br.com.parquimetro2adjt.application.response.CondutorResponseDTO;
import br.com.parquimetro2adjt.domain.entity.Condutor;
import br.com.parquimetro2adjt.domain.enums.PagamentoEnum;
import br.com.parquimetro2adjt.infra.repository.CondutorRepository;
import br.com.parquimetro2adjt.utils.Utils;
import org.springframework.stereotype.Service;

@Service
public class FormaPagamentoService {

    private final CondutorRepository condutorRepository;
    private final CondutorService condutorService;
    private final Utils utils;

    public FormaPagamentoService(CondutorRepository condutorRepository, Utils utils, CondutorService condutorService) {
        this.condutorRepository = condutorRepository;
        this.utils = utils;
        this.condutorService = condutorService;
    }

    public CondutorResponseDTO atualizar(String idCondutor, PagamentoEnum formaPagamento) {
        Condutor condutor = condutorService.buscaPorId(idCondutor);

        condutor.setFormaPagamento(formaPagamento);

        return condutorService.toResponseDto(condutorRepository.save(condutor));
    }

}
