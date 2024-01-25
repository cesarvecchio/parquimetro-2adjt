package br.com.parquimetro2adjt.application.controller;

import br.com.parquimetro2adjt.application.documentation.EstacionamentoDocController;
import br.com.parquimetro2adjt.application.request.EstacionamentoRequestDTO;
import br.com.parquimetro2adjt.application.response.EstacionamentoResponseDTO;
import br.com.parquimetro2adjt.domain.service.EstacionamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estacionamentos")
public class EstacionamentoController implements EstacionamentoDocController<EstacionamentoResponseDTO> {

    private EstacionamentoService estacionamentoService;

    public EstacionamentoController(EstacionamentoService estacionamentoService) {
        this.estacionamentoService = estacionamentoService;
    }

    @PostMapping("/{placa}")
    public ResponseEntity<EstacionamentoResponseDTO> create(@PathVariable String placa, @RequestBody EstacionamentoRequestDTO requestDTO) {
        return ResponseEntity.ok(estacionamentoService.create(placa, requestDTO));
    }

    @GetMapping("/{placa}")
    public ResponseEntity<List<EstacionamentoResponseDTO>> buscarTodosEstacionamentoPorPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(estacionamentoService.buscarTodosEstacionamentoPorPlaca(placa));
    }

    @GetMapping("/{placa}/ativo")
    public ResponseEntity<EstacionamentoResponseDTO> buscarEstacionamentoAtivoPorPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(estacionamentoService.buscarEstacionamentoAtivoPorPlaca(placa));
    }

    @GetMapping("/{placa}/finalizado")
    public ResponseEntity<List<EstacionamentoResponseDTO>> buscarEstacionamentoFinalizadoPorPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(estacionamentoService.buscarEstacionamentoFinalizadoPorPlaca(placa));
    }

    @GetMapping
    public ResponseEntity<List<EstacionamentoResponseDTO>> buscarEstacionamentosAtivos() {
        return ResponseEntity.ok(estacionamentoService.buscarEstacionamentosAtivos());
    }

    @PutMapping("/{placa}")
    public ResponseEntity<EstacionamentoResponseDTO> finalizar(@PathVariable String placa) {
        return ResponseEntity.ok(estacionamentoService.finalizar(placa));
    }
}
