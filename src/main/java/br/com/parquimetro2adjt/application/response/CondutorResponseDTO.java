package br.com.parquimetro2adjt.application.response;


import br.com.parquimetro2adjt.domain.entity.Veiculo;
import br.com.parquimetro2adjt.domain.enums.PagamentoEnum;
import br.com.parquimetro2adjt.domain.valueObject.Endereco;

import java.util.List;

public record CondutorResponseDTO(
        String id,
        String nome,
        String email,
        String cpf,
        PagamentoEnum formaPagamento,
        Endereco endereco,
        List<Veiculo> veiculos
) {
}
