package br.com.pedagogicoEhi.pedagogico_ehi.controller;

import br.com.pedagogicoEhi.pedagogico_ehi.entity.Aluno;
import br.com.pedagogicoEhi.pedagogico_ehi.exceptions.NotFoundException;
import br.com.pedagogicoEhi.pedagogico_ehi.service.AlunoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AlunoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AlunoService alunoService;

    @InjectMocks
    private AlunoController alunoController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(alunoController)
            .setControllerAdvice(new org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler() {})
            .build();
    }

    @Test
    void matricularAluno_deveRetornar200EAlunoCriado() throws Exception {
        Aluno aluno = new Aluno("João", 10, "joao@email.com");
        when(alunoService.matricular(any())).thenReturn(aluno);

        mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"João\",\"pontos\":10,\"email\":\"joao@email.com\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("João"))
            .andExpect(jsonPath("$.pontos").value(10));
    }

    @Test
    void getAlunos_deveRetornarListaDeAlunos() throws Exception {
        List<Aluno> alunos = List.of(
            new Aluno("João", 10, "joao@email.com"),
            new Aluno("Maria", 20, "maria@email.com")
        );
        when(alunoService.listarTodos()).thenReturn(alunos);

        mockMvc.perform(get("/alunos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].nome").value("João"))
            .andExpect(jsonPath("$[1].nome").value("Maria"));
    }

    @Test
    void buscarPorId_deveRetornarAlunoQuandoExistir() throws Exception {
        Aluno aluno = new Aluno("João", 10, "joao@email.com");
        UUID id = aluno.getId();
        when(alunoService.buscarPorId(id)).thenReturn(aluno);

        mockMvc.perform(get("/alunos/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void buscarPorId_deveRetornar404QuandoNaoExistir() throws Exception {
        UUID id = UUID.randomUUID();
        when(alunoService.buscarPorId(id)).thenThrow(new NotFoundException("Aluno com id " + id + " não encontrado"));

        mockMvc.perform(get("/alunos/{id}", id))
            .andExpect(status().isNotFound());
    }

    @Test
    void editarAluno_deveRetornarAlunoAtualizado() throws Exception {
        Aluno atualizado = new Aluno("João Atualizado", 50, "joao@email.com");
        UUID id = atualizado.getId();
        when(alunoService.editar(eq(id), any())).thenReturn(atualizado);

        mockMvc.perform(patch("/alunos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"João Atualizado\",\"pontos\":50,\"email\":\"joao@email.com\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("João Atualizado"))
            .andExpect(jsonPath("$.pontos").value(50));
    }

    @Test
    void editarAluno_deveRetornar404QuandoNaoExistir() throws Exception {
        UUID id = UUID.randomUUID();
        when(alunoService.editar(eq(id), any())).thenThrow(new NotFoundException("Aluno com id " + id + " não encontrado"));

        mockMvc.perform(patch("/alunos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"X\",\"pontos\":0,\"email\":\"x@email.com\"}"))
            .andExpect(status().isNotFound());
    }

    @Test
    void deletarAluno_deveRetornar200() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(alunoService).deletar(id);

        mockMvc.perform(delete("/alunos/{id}", id))
            .andExpect(status().isOk());
    }

    @Test
    void deletarAluno_deveRetornar404QuandoNaoExistir() throws Exception {
        UUID id = UUID.randomUUID();
        doThrow(new NotFoundException("Aluno com id " + id + " não encontrado")).when(alunoService).deletar(id);

        mockMvc.perform(delete("/alunos/{id}", id))
            .andExpect(status().isNotFound());
    }
}
