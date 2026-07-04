package br.com.pedagogicoEhi.pedagogico_ehi.repository;

import br.com.pedagogicoEhi.pedagogico_ehi.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, UUID> {
}
