package br.com.parquimetro2adjt.application.controller;

import br.com.parquimetro2adjt.application.documentation.ReciboDocController;
import br.com.parquimetro2adjt.domain.enums.TipoEstacionamentoEnum;
import br.com.parquimetro2adjt.domain.service.ReciboService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/recibos")
public class ReciboController implements ReciboDocController<String> {

    private final ReciboService reciboService;

    public ReciboController(ReciboService reciboService) {
        this.reciboService = reciboService;
    }

    @GetMapping(path = "/{placa}",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> emitir(@PathVariable String placa){
        return ResponseEntity.ok(reciboService.gerarRecibo(placa));
    }
}
