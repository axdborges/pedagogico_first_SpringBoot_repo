# Pedagógico EHI

Demo de um sistema pedagógico da EHI (Escola de História da Igreja) que computa aulas e pontos de alunos.

## Tecnologias

- Java 21
- Spring Boot 4.0.3 (Web MVC, Data JPA)
- H2 Database (banco em memória)
- Lombok
- Maven

## Como executar

```bash
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

Console do H2 disponível em `http://localhost:8080/h2-console`
(JDBC URL: `jdbc:h2:mem:pedagogicodb`, usuário: `sa`, senha: em branco).

## Modelo: Aluno

| Campo   | Tipo   | Descrição              |
|---------|--------|------------------------|
| id      | UUID   | Gerado automaticamente |
| nome    | String | Nome do aluno          |
| pontos  | int    | Pontuação do aluno     |
| email   | String | E-mail do aluno        |

## Endpoints

Base path: `/alunos`

### Criar/matricular aluno

```
POST /alunos
```

Corpo (JSON):
```json
{
  "nome": "Maria Silva",
  "pontos": 0,
  "email": "maria@exemplo.com"
}
```

Resposta: `200 OK` com o aluno criado (incluindo `id` gerado).

### Listar todos os alunos

```
GET /alunos
```

Resposta: `200 OK` com um array de alunos.

### Buscar aluno por ID

```
GET /alunos/{id}
```

Resposta: `200 OK` com o aluno encontrado, ou `404 Not Found` caso não exista.

### Buscar alunos por query params (nome/pontos)

```
GET /alunos/params?pontos={pontos}&nome={nome}
```

Ambos os parâmetros são opcionais. Retorna uma string descritiva de teste, ex.: `Alunos com pontos 10 e com nome Maria`.

### Editar aluno

```
PATCH /alunos/{id}
```

Corpo (JSON):
```json
{
  "nome": "Maria Silva Souza",
  "pontos": 15
}
```

Atualiza `nome` e `pontos` do aluno. Resposta: `200 OK` com o aluno atualizado, ou `404 Not Found` caso não exista.

### Remover aluno

```
DELETE /alunos/{id}
```

Resposta: `200 OK` (sem corpo), ou `404 Not Found` caso não exista.

## Tratamento de erros

Requisições para IDs inexistentes retornam `404 Not Found` com mensagem descritiva (`NotFoundException`).
