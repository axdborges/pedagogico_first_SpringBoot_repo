package br.com.pedagogicoEhi.pedagogico_ehi.service;

import br.com.pedagogicoEhi.pedagogico_ehi.entity.Aluno;
import br.com.pedagogicoEhi.pedagogico_ehi.exceptions.NotFoundException;
import br.com.pedagogicoEhi.pedagogico_ehi.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno matricular(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(UUID id) {
        return alunoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Aluno com id " + id + " não encontrado"));
    }

    public Aluno editar(UUID id, Aluno payload) {
        Aluno aluno = alunoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Aluno com id " + id + " não encontrado"));
        aluno.setNome(payload.getNome());
        aluno.setPontos(payload.getPontos());
        return alunoRepository.save(aluno);
    }

    public void deletar(UUID id) {
        if (!alunoRepository.existsById(id)) {
            throw new NotFoundException("Aluno com id " + id + " não encontrado");
        }
        alunoRepository.deleteById(id);
    }
}
