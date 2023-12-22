package br.com.parquimetro2adjt.application.request;

import br.com.parquimetro2adjt.domain.enums.PagamentoEnum;
import br.com.parquimetro2adjt.domain.valueObject.Endereco;

public record CondutorRequestDTO(
        String nome,
        String email,
        PagamentoEnum formaPagamento,
        Endereco endereco
) {
}
