package br.com.parquimetro2adjt.infra.repository;

import br.com.parquimetro2adjt.domain.entity.Condutor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CondutorRepository extends MongoRepository<Condutor, String> {
    Optional<Condutor> findByCpf(String cpf);

    Boolean existsByCpf(String cpf);

    Optional<Condutor> findByEmailAndSenha(String email, String senha);

    //Metodos do veiculos

    @Query("{ 'veiculos' : { $elemMatch : { placa : ?0 } } }")
    Optional<Condutor> findVeiculoPorPlaca(String id);
}
