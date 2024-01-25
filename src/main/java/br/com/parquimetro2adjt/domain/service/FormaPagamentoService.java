package br.com.parquimetro2adjt.domain.service;

import br.com.parquimetro2adjt.application.response.CondutorResponseDTO;
import br.com.parquimetro2adjt.domain.entity.Condutor;
import br.com.parquimetro2adjt.domain.enums.PagamentoEnum;
import br.com.parquimetro2adjt.infra.repository.CondutorRepository;
import br.com.parquimetro2adjt.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FormaPagamentoService {
    private final Logger logger = LoggerFactory.getLogger(FormaPagamentoService.class);
    private final CondutorRepository condutorRepository;
    private final CondutorService condutorService;
    private final Utils utils;

    public FormaPagamentoService(CondutorRepository condutorRepository, Utils utils, CondutorService condutorService) {
        this.condutorRepository = condutorRepository;
        this.utils = utils;
        this.condutorService = condutorService;
    }

    public CondutorResponseDTO atualizar(String idCondutor, PagamentoEnum formaPagamento) {
        logger.info("Buscando condutor com id '{}'", idCondutor);
        Condutor condutor = condutorService.buscaPorId(idCondutor);

        logger.info("Atualizando a forma de pagamento para '{}'", formaPagamento);
        condutor.setFormaPagamento(formaPagamento);

        logger.info("Forma de pagamento atualizada com sucesso!");
        return condutorService.toResponseDto(condutorRepository.save(condutor));
    }

}
