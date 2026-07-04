package br.com.pedagogicoEhi.pedagogico_ehi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Aluno {
    @Id
    private UUID id;
    private String nome;
    private int pontos;
    private String email;

    public Aluno(String nome, int pontos, String email) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.pontos = pontos;
        this.email = email;
    }

    public void addPontos(int pontos) {
        this.pontos += pontos;
    }
}
