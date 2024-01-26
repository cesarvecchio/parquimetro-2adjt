package br.com.parquimetro2adjt.application.documentation;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Recibo", description = "API do recibo")
public interface ReciboDocController<T> {

    @Operation(summary = "Endpoint para emitir o recibo", description = "Emiti o recibo com todas as informações relacionadas ao estacionamento")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<String> emitir(@Parameter(description = "Placa do veiculo") String placa);
}
