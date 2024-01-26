package br.com.parquimetro2adjt.application.controller;

import br.com.parquimetro2adjt.application.documentation.VeiculoDocController;
import br.com.parquimetro2adjt.application.request.VeiculoRequestDTO;
import br.com.parquimetro2adjt.application.response.VeiculoResponseDTO;
import br.com.parquimetro2adjt.domain.service.VeiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController implements VeiculoDocController<VeiculoResponseDTO> {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping("/condutor/{idCondutor}")
    public ResponseEntity<List<VeiculoResponseDTO>> buscarTodosPorCodutor(@PathVariable String idCondutor) {
        return ResponseEntity.ok(veiculoService.findAllByCondutor(idCondutor));
    }

    @GetMapping("/{placa}")
    public ResponseEntity<VeiculoResponseDTO> buscar(@PathVariable String placa) {
        return ResponseEntity.ok(veiculoService.buscar(placa));
    }

    @PostMapping("/{idCondutor}")
    public ResponseEntity<VeiculoResponseDTO> cadastrar(@PathVariable String idCondutor, @RequestBody VeiculoRequestDTO requestDTO) {
        return ResponseEntity.ok(veiculoService.cadastrar(idCondutor, requestDTO));
    }

    @PutMapping("/{placa}")
    public ResponseEntity<VeiculoResponseDTO> atualizar(@PathVariable String placa, @RequestBody VeiculoRequestDTO requestDTO) {
        return ResponseEntity.ok(veiculoService.atualizar(placa, requestDTO));
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<VeiculoResponseDTO> deletar(@PathVariable String placa) {
        veiculoService.deletar(placa);

        return ResponseEntity.noContent().build();
    }
}
