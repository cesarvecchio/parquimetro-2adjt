package br.com.parquimetro2adjt.application.controller;

import br.com.parquimetro2adjt.application.request.VeiculoRequestDTO;
import br.com.parquimetro2adjt.application.response.VeiculoResponseDTO;
import br.com.parquimetro2adjt.domain.entity.Condutor;
import br.com.parquimetro2adjt.domain.service.VeiculoService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping
    public ResponseEntity<Page<VeiculoResponseDTO>> findAll(@PageableDefault(size = 10, page = 0, sort = "placa") Pageable pageable,
                                                            @RequestParam(required = false) String placa,
                                                            @RequestParam(required = false) String modelo,
                                                            @RequestParam(required = false) String cor,
                                                            @RequestParam(required = false) String marca,
                                                            @RequestParam(required = false) Condutor condutor) {
        return ResponseEntity.ok(veiculoService.findAll(pageable, placa, modelo, cor, marca, condutor));
    }

    @PostMapping("/{idCondutor}")
    public ResponseEntity<VeiculoResponseDTO> cadastrar(@PathVariable String idCondutor, @RequestBody VeiculoRequestDTO veiculoDTO) {
        return ResponseEntity.ok(veiculoService.cadastrar(veiculoDTO, idCondutor));
    }

    @PutMapping("/{idCondutor}/{idVeiculo}")
    public ResponseEntity<VeiculoResponseDTO> atualizar(@PathVariable String idCondutor, @PathVariable String idVeiculo,
                                                    @RequestBody VeiculoRequestDTO veiculoDTO) {
        return ResponseEntity.ok(veiculoService.atualizar(idCondutor, idVeiculo, veiculoDTO));
    }

    @DeleteMapping("/{idCondutor}/{idVeiculo}")
    public ResponseEntity<Void> deletar(@PathVariable String idCondutor, @PathVariable String idVeiculo) {
        veiculoService.deletar(idCondutor, idVeiculo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/Condutor/{idCondutor}")
    public ResponseEntity<Page<VeiculoResponseDTO>> consultarPetsPorCondutor(@PathVariable String idCondutor,
                                                                     @PageableDefault(size = 10, page = 0, sort = "placa") Pageable pageable) {
        return ResponseEntity.ok(veiculoService.consultarVeiculosPorCondutor(idCondutor, pageable));
    }

}
