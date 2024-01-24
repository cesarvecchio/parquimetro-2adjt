package br.com.parquimetro2adjt.domain.entity;

import br.com.parquimetro2adjt.domain.enums.PagamentoEnum;
import br.com.parquimetro2adjt.domain.valueObject.Endereco;
import br.com.parquimetro2adjt.domain.valueObject.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Condutor {

    @Id
    private String id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private PagamentoEnum formaPagamento;
    private Endereco endereco;
    private List<Veiculo> veiculos;

}
