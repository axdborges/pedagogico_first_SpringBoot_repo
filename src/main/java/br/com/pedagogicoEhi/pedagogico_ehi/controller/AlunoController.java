package br.com.pedagogicoEhi.pedagogico_ehi.controller;

import br.com.pedagogicoEhi.pedagogico_ehi.entity.Aluno;
import br.com.pedagogicoEhi.pedagogico_ehi.service.AlunoService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public Aluno matricularAluno(@RequestBody Aluno payload) {
        return alunoService.matricular(payload);
    }

    @GetMapping
    public List<Aluno> getAlunos() {
        return alunoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Aluno listaAlunoPorId(@PathVariable UUID id) {
        return alunoService.buscarPorId(id);
    }

    @GetMapping("/params")
    public String testarQueryParam(@RequestParam Optional<Integer> pontos, @RequestParam Optional<String> nome) {
        String resposta = "Alunos";

        if (!pontos.isEmpty()) resposta += " com pontos " + pontos.get();

        if (!nome.isEmpty()) resposta += " e com nome " + nome.get();

        return resposta;
    }

    @PatchMapping("/{id}")
    public Aluno editarAluno(@PathVariable UUID id, @RequestBody Aluno payload) {
        return alunoService.editar(id, payload);
    }

    @DeleteMapping("/{id}")
    public void deletarAluno(@PathVariable UUID id) {
        alunoService.deletar(id);
    }
}
