package br.com.pedagogicoEhi.pedagogico_ehi.service;

import br.com.pedagogicoEhi.pedagogico_ehi.entity.Aluno;
import br.com.pedagogicoEhi.pedagogico_ehi.exceptions.NotFoundException;
import br.com.pedagogicoEhi.pedagogico_ehi.repository.AlunoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    @Test
    void matricular_deveSalvarERetornarAluno() {
        Aluno aluno = new Aluno("João", 10, "joao@email.com");
        when(alunoRepository.save(aluno)).thenReturn(aluno);

        Aluno resultado = alunoService.matricular(aluno);

        assertThat(resultado).isEqualTo(aluno);
        verify(alunoRepository).save(aluno);
    }

    @Test
    void listarTodos_deveRetornarListaDeAlunos() {
        List<Aluno> alunos = List.of(
            new Aluno("João", 10, "joao@email.com"),
            new Aluno("Maria", 20, "maria@email.com")
        );
        when(alunoRepository.findAll()).thenReturn(alunos);

        List<Aluno> resultado = alunoService.listarTodos();

        assertThat(resultado).hasSize(2);
        verify(alunoRepository).findAll();
    }

    @Test
    void buscarPorId_deveRetornarAlunoQuandoExistir() {
        Aluno aluno = new Aluno("João", 10, "joao@email.com");
        UUID id = aluno.getId();
        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));

        Aluno resultado = alunoService.buscarPorId(id);

        assertThat(resultado).isEqualTo(aluno);
        verify(alunoRepository).findById(id);
    }

    @Test
    void buscarPorId_deveLancarNotFoundQuandoNaoExistir() {
        UUID id = UUID.randomUUID();
        when(alunoRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> alunoService.buscarPorId(id))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(id.toString());
    }

    @Test
    void editar_deveAtualizarAlunoQuandoExistir() {
        Aluno existente = new Aluno("João", 10, "joao@email.com");
        UUID id = existente.getId();
        Aluno payload = new Aluno("João Atualizado", 50, "joao@email.com");

        when(alunoRepository.findById(id)).thenReturn(Optional.of(existente));
        when(alunoRepository.save(existente)).thenReturn(existente);

        Aluno resultado = alunoService.editar(id, payload);

        assertThat(resultado.getNome()).isEqualTo("João Atualizado");
        assertThat(resultado.getPontos()).isEqualTo(50);
        verify(alunoRepository).save(existente);
    }

    @Test
    void editar_deveLancarNotFoundQuandoNaoExistir() {
        UUID id = UUID.randomUUID();
        Aluno payload = new Aluno("João", 10, "joao@email.com");
        when(alunoRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> alunoService.editar(id, payload))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(id.toString());

        verify(alunoRepository, never()).save(any());
    }

    @Test
    void deletar_deveChamarDeleteByIdQuandoExistir() {
        UUID id = UUID.randomUUID();
        when(alunoRepository.existsById(id)).thenReturn(true);

        alunoService.deletar(id);

        verify(alunoRepository).deleteById(id);
    }

    @Test
    void deletar_deveLancarNotFoundQuandoNaoExistir() {
        UUID id = UUID.randomUUID();
        when(alunoRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> alunoService.deletar(id))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(id.toString());

        verify(alunoRepository, never()).deleteById(any());
    }
}
