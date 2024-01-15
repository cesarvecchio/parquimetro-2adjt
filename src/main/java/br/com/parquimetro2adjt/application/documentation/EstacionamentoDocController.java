package br.com.parquimetro2adjt.application.documentation;

import br.com.parquimetro2adjt.application.request.EstacionamentoRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Estacionamento", description = "API de estacionamento")
public interface EstacionamentoDocController<T> {
    @Operation(summary = "Endpoint para cadastrar um estacionamento", description = "Cadastrar um estacionamento na base de dados")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<T> create(@Parameter(description = "Placa do carro") String placa, @RequestBody(description = "Informações do estacionamento") EstacionamentoRequestDTO requestDTO);

    @Operation(summary = "Endpoint para buscar todos os estacionamento por placa", description = "Buscar todos os estacionamentos por placa na base de dados")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<List<T>> buscarTodosEstacionamentoPorPlaca(@Parameter(description = "Placa do carro") String placa);

    @Operation(summary = "Endpoint para buscar estacionamento ativo por placa", description = "Buscar estacionamento ativo por placa na base de dados")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<T> buscarEstacionamentoAtivoPorPlaca(@Parameter(description = "Placa do carro") String placa);

    @Operation(summary = "Endpoint para buscar estacionamento finalizado por placa", description = "Buscar o estacionamentos finalizado por placa na base de dados")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<T> buscarEstacionamentoFinalizadoPorPlaca(@Parameter(description = "Placa do carro") String placa);

    @Operation(summary = "Endpoint para buscar todos os estacionamento ativos", description = "Buscar todos os estacionamentos ativos na base de dados")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<List<T>> buscarEstacionamentosAtivos();

    @Operation(summary = "Endpoint para finalizar um estacionamento", description = "Finaliza um estacionamento, calculando o valor a ser pago")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<T> finalizar(@Parameter(description = "Placa do carro") String placa);

}
