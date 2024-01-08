package br.com.parquimetro2adjt.application.controller;

import br.com.parquimetro2adjt.application.request.VeiculoRequestDTO;
import br.com.parquimetro2adjt.application.response.VeiculoResponseDTO;
import br.com.parquimetro2adjt.application.response.VeiculoResponseDTO2;
import br.com.parquimetro2adjt.domain.entity.Condutor;
import br.com.parquimetro2adjt.domain.entity.Veiculo;
import br.com.parquimetro2adjt.domain.service.VeiculoService2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos2")
public class VeiculoController2 {

    private final VeiculoService2 veiculoService;

    public VeiculoController2(VeiculoService2 veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping("/condutor/{idCondutor}")
    public ResponseEntity<List<VeiculoResponseDTO2>> buscarTodosPorCodutor(@PathVariable String idCondutor) {
        return ResponseEntity.ok(veiculoService.findAllByCondutor(idCondutor));
    }

    @GetMapping("/{placa}")
    public ResponseEntity<VeiculoResponseDTO2> buscar(@PathVariable String placa) {
        return ResponseEntity.ok(veiculoService.buscar(placa));
    }

    @PostMapping("/{idCondutor}")
    public ResponseEntity<VeiculoResponseDTO2> cadastrar(@PathVariable String idCondutor, @RequestBody VeiculoRequestDTO requestDTO) {
        return ResponseEntity.ok(veiculoService.cadastrar(idCondutor, requestDTO));
    }

    @PutMapping("/{placa}")
    public ResponseEntity<VeiculoResponseDTO2> atualizar(@PathVariable String placa, @RequestBody VeiculoRequestDTO requestDTO) {
        return ResponseEntity.ok(veiculoService.atualizar(placa, requestDTO));
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<VeiculoResponseDTO2> deletar(@PathVariable String placa) {
        veiculoService.deletar(placa);

        return ResponseEntity.noContent().build();
    }
}
