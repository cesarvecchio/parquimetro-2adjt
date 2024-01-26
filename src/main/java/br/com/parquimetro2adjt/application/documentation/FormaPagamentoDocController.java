package br.com.parquimetro2adjt.application.documentation;

import br.com.parquimetro2adjt.application.response.CondutorResponseDTO;
import br.com.parquimetro2adjt.domain.enums.PagamentoEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Forma de Pagamento", description = "API da forma de pagamento")
public interface FormaPagamentoDocController<T> {

    @Operation(summary = "Endpoint para atualizar a forma de pagamento do condutor", description = "Atualizar a forma de pagamento na base de dados")
    @ApiResponse(useReturnTypeSchema = true)
    ResponseEntity<CondutorResponseDTO> atualizar(@Parameter(description = "Id do condutor") String idCondutor,
                                                         @Parameter(description = "Forma de pagamento") PagamentoEnum formaPagamento);
}
