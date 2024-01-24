package br.com.parquimetro2adjt.application.documentation;

import br.com.parquimetro2adjt.application.request.CondutorRequestDTO;
import br.com.parquimetro2adjt.domain.valueObject.Veiculo;
import br.com.parquimetro2adjt.domain.enums.PagamentoEnum;
import br.com.parquimetro2adjt.domain.valueObject.Endereco;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Condutor", description = "API de condutor")
public interface CondutorDocController<R> {

    @Operation(summary = "Endpoint para cadastrar condutor", description = "Cadastrar condutor na base de dados")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<R> cadastrar(@RequestBody(description = "As informações relevantes para o cadastro do condutor") CondutorRequestDTO condutorDTO);

    @Operation(summary = "Endpoint para consultar condutores", description = "Consultar condutores cadastrados na base de dados")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<Page<R>> findAll(@Parameter(description = "Paginação") Pageable pageable,
                                    @Parameter(description = "Nome do condutor") String nome,
                                    @Parameter(description = "Email do condutor") String email,
                                    @Parameter(description = "CPF do condutor") String cpf,
                                    @Parameter(description = "Forma de pagamento favorita") PagamentoEnum formaPagamento,
                                    @Parameter(description = "Endereço do condutor") Endereco endereco,
                                    @Parameter(description = "Veículos do condutor") List<Veiculo> veiculos);

    @Operation(summary = "Endpoint para consultar condutor por ID", description = "Consultar condutor cadastrado na base de dados por ID")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<R> consultarPorId(@Parameter(description = "ID do condutor") String id);

    @Operation(summary = "Endpoint para atualizar condutor", description = "Atualizar condutor cadastrado na base de dados")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<R> atualizar(@Parameter(description = "ID do condutor") String id,
                                @RequestBody(description = "Os dados de atualização do condutor") CondutorRequestDTO condutorRequestDTO);

    @Operation(summary = "Endpoint para deletar condutor", description = "Deletar condutor cadastrado na base de dados")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<Void> deletar(@Parameter(description = "ID do condutor") String id);
}
