package br.com.parquimetro2adjt.application.controller;

import br.com.parquimetro2adjt.application.request.CondutorRequestDTO;
import br.com.parquimetro2adjt.application.response.CondutorResponseDTO;
import br.com.parquimetro2adjt.domain.entity.Condutor;
import br.com.parquimetro2adjt.domain.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/condutores")
public class CondutorController {

    @Autowired
    private CondutorService condutorService;

    @PostMapping
    public ResponseEntity<CondutorResponseDTO> create(@RequestBody CondutorRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(condutorService.create(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<Condutor>> getAll(){
        return ResponseEntity.ok(condutorService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CondutorResponseDTO> update(@PathVariable String id, @RequestBody CondutorRequestDTO requestDTO) {
        return ResponseEntity.ok(this.condutorService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        this.condutorService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
