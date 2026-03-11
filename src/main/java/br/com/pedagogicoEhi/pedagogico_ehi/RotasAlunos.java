package br.com.pedagogicoEhi.pedagogico_ehi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alunos")
public class RotasAlunos {

    private List<Aluno> alunos = new ArrayList<>();

    @PostMapping
    public String matricularAluno(@RequestBody Aluno payload) {
        alunos.add(payload);
        return "O Aluno recebido foi: " + payload.getNome() + ". Agora temos que tratar JSON";
    }

    @GetMapping
    public List<Aluno> getAlunos() {
        return alunos;
    }

    @GetMapping("/{id}")
    public Optional<Aluno> listaAlunoPorId(@PathVariable UUID id) {
        return alunos.stream().filter(a -> a.getId().equals(id)).findFirst();
        // .orElseThrow(() -> new NotFoundException("Aluno com id " + id + " não encontrado"));
    }

    @GetMapping("/params")
    public String testarQueryParam(@RequestParam Optional<Integer> pontos, @RequestParam Optional<String> nome) {
        String resposta = "Alunos";

        if (!pontos.isEmpty()) resposta += " com pontos " + pontos.get();

        if (!nome.isEmpty()) resposta += " e com nome " + nome.get();

        return resposta;
    }

    @PutMapping
    public List<Aluno> setAlunos(List<Aluno> alunos) {
        if(!alunos.isEmpty()) {
            this.alunos = alunos;
        } 
        return alunos;
    }

    @PatchMapping("/{id}")
    public Aluno editarAluno(@PathVariable UUID id, @RequestBody Aluno payload){
        for(Aluno aluno : alunos) {
            if(aluno.getId().equals(id)) {
                aluno.setNome(payload.getNome());
                aluno.setPontos(payload.getPontos());
                return payload;
            }
        }
        return new Aluno("Aluno não criado", 0, "nullable@email.com");
    }

    @DeleteMapping("/{id}")
    public void deletarAluno(@PathVariable UUID id){
        alunos.removeIf(aluno-> aluno.getId().equals(id));
    }
}