package br.com.parquimetro2adjt.application.controller;

import br.com.parquimetro2adjt.application.response.CondutorResponseDTO;
import br.com.parquimetro2adjt.domain.enums.PagamentoEnum;
import br.com.parquimetro2adjt.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{idCondutor}/formaPagamento/{formaPagamento}")
public class FormaPagamentoController {

    @Autowired
    private final FormaPagamentoService formaPagamentoService;

    public FormaPagamentoController(FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = formaPagamentoService;
    }

    @PutMapping
    public ResponseEntity<CondutorResponseDTO> atualizar(@PathVariable String idCondutor, @PathVariable PagamentoEnum formaPagamento) {
        return ResponseEntity.ok(formaPagamentoService.atualizar(idCondutor, formaPagamento));
    }
}
