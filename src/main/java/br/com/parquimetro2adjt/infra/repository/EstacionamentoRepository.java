package br.com.parquimetro2adjt.infra.repository;

import br.com.parquimetro2adjt.domain.entity.Estacionamento;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EstacionamentoRepository extends MongoRepository<Estacionamento, ObjectId> {

    @Query("{ 'veiculo.placa' : ?0 }")
    List<Estacionamento> buscarTodosEstacionamentoPorPlaca(String placa);

    @Query("{ 'veiculo.placa' : ?0, horaFinal: { $eq: null } }")
    Optional<Estacionamento> buscarEstacionamentoAtivoPorPlaca(String placa);

    @Query("{ 'veiculo.placa' : ?0, horaFinal: { $ne: null } }")
    List<Estacionamento> buscarEstacionamentoFinalizadoPorPlaca(String placa);

    @Query("{ horaFinal: { $eq: null } }")
    List<Estacionamento> buscarEstacionamentosAtivos();

    @Query("{ horaFinal: { $eq: null }, tipoEstacionamento: 'FIXO', dataHoraUltimoAlerta: { $eq: null } }")
    List<Estacionamento> buscarEstacionamentosAtivosPeriodoFixoNaoNotificados();

    @Query("{ horaFinal: { $eq: null }, tipoEstacionamento: 'VARIAVEL' }")
    List<Estacionamento> buscarEstacionamentosAtivosPeriodoVariavel();
}
