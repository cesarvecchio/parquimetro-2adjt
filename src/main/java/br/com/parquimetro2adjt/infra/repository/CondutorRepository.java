package br.com.parquimetro2adjt.infra.repository;

import br.com.parquimetro2adjt.domain.entity.Condutor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CondutorRepository extends MongoRepository<Condutor, String> {

}
