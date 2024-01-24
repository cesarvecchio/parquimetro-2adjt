package br.com.parquimetro2adjt.application.controller;

import br.com.parquimetro2adjt.application.documentation.CondutorDocController;
import br.com.parquimetro2adjt.application.request.CondutorRequestDTO;
import br.com.parquimetro2adjt.application.response.CondutorResponseDTO;
import br.com.parquimetro2adjt.domain.entity.Veiculo;
import br.com.parquimetro2adjt.domain.enums.PagamentoEnum;
import br.com.parquimetro2adjt.domain.service.CondutorService;
import br.com.parquimetro2adjt.domain.valueObject.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/condutores")
public class CondutorController implements CondutorDocController<CondutorResponseDTO> {

    private final CondutorService condutorService;

    public CondutorController(CondutorService condutorService) {
        this.condutorService = condutorService;
    }

    @PostMapping
    public ResponseEntity<CondutorResponseDTO> cadastrar(@RequestBody CondutorRequestDTO condutorDTO) {
        return ResponseEntity.ok(condutorService.cadastrar(condutorDTO));
    }

    @GetMapping
    public ResponseEntity<Page<CondutorResponseDTO>> findAll(@PageableDefault(size = 10, page = 0, sort = "nome") Pageable pageable,
                                                             @RequestParam(required = false) String nome,
                                                             @RequestParam(required = false) String email,
                                                             @RequestParam(required = false) String cpf,
                                                             @RequestParam(required = false) PagamentoEnum formaPagamento,
                                                             @RequestParam(required = false) Endereco endereco,
                                                             @RequestParam(required = false) List<Veiculo> veiculos) {
        return ResponseEntity.ok(condutorService.findAll(pageable, nome, email, cpf, formaPagamento, endereco, veiculos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CondutorResponseDTO> consultarPorId(@PathVariable String id) {
        return ResponseEntity.ok(condutorService.toResponseDto(condutorService.buscaPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CondutorResponseDTO> atualizar(@PathVariable String id, @RequestBody CondutorRequestDTO condutorRequestDTO) {
        return ResponseEntity.ok(condutorService.atualizar(id, condutorRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        condutorService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
