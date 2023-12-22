package br.com.parquimetro2adjt.application.response;


import br.com.parquimetro2adjt.domain.enums.PagamentoEnum;
import br.com.parquimetro2adjt.domain.valueObject.Endereco;

import java.util.List;

public record CondutorResponseDTO(
        String id,
        String nome,
        String email,
        PagamentoEnum formaPagamento,
        Endereco endereco,
        List<VeiculoResponseDTO> veiculos
) {
}
