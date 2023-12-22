package br.com.parquimetro2adjt.domain.entity;

import br.com.parquimetro2adjt.domain.enums.PagamentoEnum;
import br.com.parquimetro2adjt.domain.valueObject.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Condutor {

    @Id
    private String id;
    private String nome;
    private String email;
    private PagamentoEnum formaPagamento;
    private Endereco endereco;
    private List<Veiculo> veiculos;

}
