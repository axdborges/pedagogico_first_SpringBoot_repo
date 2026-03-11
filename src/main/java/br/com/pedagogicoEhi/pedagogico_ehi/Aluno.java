package br.com.pedagogicoEhi.pedagogico_ehi;

import java.util.UUID;

public class Aluno {
    private UUID id;
    private String nome;
    private int pontos;
    private String email;

    public Aluno(String nome, int pontos, String email){
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
        this.pontos = pontos;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public void addPontos(int pontos) {
        this.pontos += pontos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}