package br.com.parquimetro2adjt.application.documentation;

import br.com.parquimetro2adjt.application.request.VeiculoRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Veiculo", description = "API de veiculo")
public interface VeiculoDocController<T> {

    @Operation(summary = "Endpoint para buscar todos os veículos do condutor", description = "Busca todos os veículos do condutor na base de dados")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<List<T>> buscarTodosPorCodutor(@Parameter(description = "ID do condutor") String idCondutor);

    @Operation(summary = "Endpoint para buscar um veículo", description = "Busca um veículo na base de dados")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<T> buscar(@Parameter(description = "Placa do veículo") String placa);

    @Operation(summary = "Endpoint para cadastrar um veículo", description = "Cadastra um veículo vinculado a um condutor na base de dados")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<T> cadastrar(@Parameter(description = "ID do condutor") String idCondutor,
                                @RequestBody(description = "Dados do veículo para serem cadastrados") VeiculoRequestDTO requestDTO);

    @Operation(summary = "Endpoint para atualizar um veículo", description = "Atualiza um veículo base de dados")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<T> atualizar(@Parameter(description = "Placa do veículo") String placa,
                                @RequestBody(description = "Dados do veículo para serem cadastrados") VeiculoRequestDTO requestDTO);

    @Operation(summary = "Endpoint para deletar um veículo", description = "Deleta um veículo base de dados")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<T> deletar(@Parameter(description = "Placa do veículo") String placa);
}
