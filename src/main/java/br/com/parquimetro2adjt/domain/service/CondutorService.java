package br.com.parquimetro2adjt.domain.service;

import br.com.parquimetro2adjt.application.request.CondutorRequestDTO;
import br.com.parquimetro2adjt.application.response.CondutorResponseDTO;
import br.com.parquimetro2adjt.domain.entity.Condutor;
import br.com.parquimetro2adjt.infra.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CondutorService {
    @Autowired
    private CondutorRepository condutorRepository;

    public CondutorResponseDTO create(CondutorRequestDTO requestDTO) {
        return toResponseDTO(condutorRepository.save(toCondutor(requestDTO)));
    }

    public List<Condutor> getAll() {
        return this.condutorRepository.findAll();
    }

    public CondutorResponseDTO update(String id, CondutorRequestDTO requestDTO) {
        Condutor condutor = this.condutorRepository.findById(id).get();

        condutor.setNome(requestDTO.nome());
        condutor.setEmail(requestDTO.email());
        condutor.setFormaPagamento(requestDTO.formaPagamento());
        condutor.setEndereco(requestDTO.endereco());

        return toResponseDTO(this.condutorRepository.save(condutor));
    }

    public void delete(String id){
        this.condutorRepository.deleteById(id);
    }

    public Condutor toCondutor(CondutorRequestDTO requestDTO) {
        Condutor condutor = new Condutor();

        condutor.setNome(requestDTO.nome());
        condutor.setEmail(requestDTO.email());
        condutor.setFormaPagamento(requestDTO.formaPagamento());
        condutor.setEndereco(requestDTO.endereco());

        return condutor;
    }

    public CondutorResponseDTO toResponseDTO(Condutor condutor) {
        return new CondutorResponseDTO(
                condutor.getId(),
                condutor.getNome(),
                condutor.getEmail(),
                condutor.getFormaPagamento(),
                condutor.getEndereco(),
                null);
    }
}
