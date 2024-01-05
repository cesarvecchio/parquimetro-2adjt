package br.com.parquimetro2adjt.infra.repository;

import br.com.parquimetro2adjt.domain.entity.Veiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VeiculoRepository extends MongoRepository<Veiculo, String> {
    boolean existsVeiculoByIdAndCondutorId(String id, String idCondutor);

    Page<Veiculo> findAllByCondutorId(String idCondutor, Pageable pageable);
}
