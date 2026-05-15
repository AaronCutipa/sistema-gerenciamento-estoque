# 📦 Sistema de Gerenciamento de Estoque

API REST desenvolvida com **Java + Spring Boot** para gerenciamento de estoque de produtos.

Este projeto foi desenvolvido como atividade acadêmica de recuperação e também como parte de portfólio, com foco em:

- Boas práticas de desenvolvimento backend
- Arquitetura em camadas
- Validação de dados
- Tratamento de exceções
- Regras de negócio
- Testes unitários
- Documentação de API

---

# 🎯 Objetivo

Construir uma API REST completa capaz de gerenciar produtos em estoque, garantindo:

- Integridade dos dados
- Validações robustas
- Regras de negócio consistentes
- Organização em camadas
- Tratamento adequado de erros HTTP
- Documentação via Swagger/OpenAPI

---

# 🏫 Informações Acadêmicas

| Campo | Informação            |
|---|-----------------------|
| Faculdade | São Paulo Tech School (SPTech) |
| Curso | Ciência da Computação |
| Disciplina | Programação WEB - Backend |
| Professor(a) | Diego Brito           |
| Semestre | 4º semestre           |
| Aluno | Aaron Cutipa          |
| Tipo da atividade | Recuperação           |
| Data de entrega | 21 de maio de 2026    |

---

# 🏗️ Arquitetura do Projeto

O sistema segue o padrão de arquitetura em camadas:

- **Controller**
  - Responsável por expor os endpoints REST da aplicação
  - Recebe requisições HTTP e retorna respostas adequadas

- **Service**
  - Contém toda a lógica de negócio
  - Realiza validações e regras do sistema

- **Repository**
  - Responsável pelo acesso e persistência dos dados
  - Utiliza Spring Data JPA

- **Model**
  - Representa as entidades da aplicação

- **DTO (Data Transfer Object)**
  - Utilizados para entrada e saída de dados da API
  - Evitam exposição direta das entidades

- **Mapper**
  - Responsável pela conversão entre entidades e DTOs

- **Exception**
  - Contém exceções customizadas da aplicação

- **Exception Handler**
  - Realiza o tratamento global de erros
  - Retorna respostas HTTP padronizadas

- **Código Único**
  - Responsável pela estratégia de geração de código único dos produtos

- **Config**
  - Configurações gerais da aplicação
  - Configuração do Swagger/OpenAPI
---

# 📂 Estrutura do Projeto

```text
src
├── main
│   ├── java
│   │   └── school.sptech.sistema_gerenciamento_estoque
│   │       ├── controller
│   │       ├── service
│   │       ├── repository
│   │       ├── model
│   │       ├── dto
│   │       ├── exception
│   │       │   └── handler
│   │       ├── codigo_unico
│   │       ├── config
│   │       └── SistemaGerenciamentoEstoqueApplication.java
│   │
│   └── resources
│       ├── application.properties
│       └── data.sql
│
└── test
    └── java
        └── school.sptech.sistema_gerenciamento_estoque
            └── service
                └── ProdutoServiceTest.java
```

---

# ⚙️ Tecnologias Utilizadas

- Java 21
- Spring Boot 3.5
- Spring Web
- Spring Data JPA
- Hibernate
- Jakarta Validation (Bean Validation)
- H2 Database
- Lombok
- Swagger / OpenAPI (springdoc)
- JUnit 5
- Mockito
- Maven

---

# 🚀 Como Executar o Projeto

## ✅ Pré-requisitos

- Java 21
- Maven 3.9+

---

## ▶️ Passo a passo

```bash
# Clonar o repositório
git clone https://github.com/seu-usuario/sistema-gerenciamento-estoque.git

# Entrar na pasta
cd sistema-gerenciamento-estoque

# Executar a aplicação
mvn spring-boot:run
```

---

# 🌐 Acesso da Aplicação

## Swagger

Após iniciar a aplicação, acessar:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Console H2

```text
http://localhost:8080/h2-console
```

### Configuração padrão

| Campo | Valor |
|---|---|
| JDBC URL | jdbc:h2:mem:testdb |
| User Name | sa |
| Password | *(vazio)* |

---

# 🔗 Endpoints da API

## 📌 Produtos

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/produtos` | Cadastrar produto |
| GET | `/produtos` | Listar produtos |
| GET | `/produtos/{id}` | Buscar produto por ID |
| PUT | `/produtos/{id}` | Atualizar produto |
| PUT | `/produtos/{id}/baixa` | Dar baixa no estoque |
| DELETE | `/produtos/{id}` | Soft delete do produto |
| GET | `/produtos?nome=&categoria=` | Filtrar produtos |

---

# 📦 Estrutura do Produto

Um produto possui os seguintes atributos:

| Campo | Descrição |
|---|---|
| id | Identificador único |
| codigo | Código único do produto |
| nome | Nome do produto |
| categoria | Categoria |
| quantidade | Quantidade em estoque |
| preco | Valor unitário |
| ativo | Status do produto |
| dataRemocao | Data do soft delete |

---

# 🧠 Estratégia de Código Único (SKU)

O sistema gera automaticamente um código único para cada produto.

## 📌 Exemplo

```text
AUD-HEADS-943FAA97
```

## 📌 Estrutura

| Parte | Descrição |
|---|---|
| AUD | Prefixo da categoria |
| HEADS | Parte do nome do produto |
| 943FAA97 | Identificador aleatório |

---

# ⚠️ Regras de Negócio

## 🔹 Código único

- Cada produto deve possuir um código único
- O código é gerado automaticamente
- Não pode existir duplicidade
- Existe validação no cadastro e atualização

---

## 🔹 Validação de dados

O sistema utiliza Bean Validation (`@Valid`) para validação automática.

### Regras aplicadas

- Campos obrigatórios não podem ser nulos
- Nome e categoria não podem ser vazios
- Quantidade deve ser ≥ 0
- Preço deve ser > 0
- Nome e categoria possuem tamanho mínimo e máximo
- Código deve seguir o padrão definido

---

## 🔹 Atualização de produto

- Caso o código seja alterado:
  - o sistema valida conflito com outros produtos
- Não permite duplicidade de código
- Produto removido logicamente não pode ser atualizado

---

## 🔹 Baixa de estoque

Regras:

- Não permite quantidade negativa
- Não permite baixa maior que o estoque disponível
- Produto deve existir
- Produto deve estar ativo

---

## 🔹 Soft Delete

A exclusão do produto é lógica.

O registro permanece salvo no banco, porém:

- `ativo = false`
- `dataRemocao` recebe a data da remoção

### Após remoção

- Produto não aparece em listagens
- Não pode ser atualizado
- Não pode sofrer baixa de estoque

---

# 📬 Respostas HTTP

| Status | Situação |
|---|---|
| 200 | Operação realizada com sucesso |
| 201 | Produto criado |
| 400 | Dados inválidos |
| 404 | Produto não encontrado |
| 409 | Código duplicado |
| 422 | Estoque insuficiente |
| 500 | Erro interno inesperado |

---

# ❌ Tratamento Global de Exceções

A aplicação possui tratamento centralizado utilizando:

```text
@RestControllerAdvice
```

As respostas de erro retornam:

- status HTTP
- tipo do erro
- mensagem
- endpoint acessado
- timestamp

## 📌 Exemplo

```json
{
  "status": 404,
  "erro": "Produto não encontrado",
  "mensagem": "Produto com ID 10 não encontrado",
  "path": "/produtos/10",
  "timestamp": "2026-05-14T20:30:00"
}
```

---

# 🧪 Testes Unitários

Foram implementados testes unitários na camada de Service utilizando:

- JUnit 5
- Mockito

## 📌 Casos testados

- Cadastro com sucesso
- Geração de código único
- Atualização com conflito de código
- Baixa de estoque válida
- Baixa maior que estoque disponível
- Busca por ID inexistente
- Soft delete com sucesso
- Operações em produto removido

---

# 🗄️ Banco de Dados

O projeto utiliza:

- H2 Database
- Banco em memória

---

# 📌 Diferenciais do Projeto

- Arquitetura organizada em camadas
- Uso de DTOs
- Soft Delete implementado
- Tratamento global de exceções
- Validações robustas
- Regras de negócio centralizadas na camada Service
- Swagger funcional
- Testes unitários
- Código único automatizado (SKU)

---

# 📈 Possíveis Melhorias Futuras

- Spring Security + JWT
- MySQL/PostgreSQL
- Docker
- Paginação
- Logs estruturados
- Deploy em nuvem
- Auditoria de ações
- Controle de usuários
- Upload de imagens de produtos

---

# 👨‍💻 Autor

## Aaron Cutipa

🎓 Ciência da Computação — 4º semestre  
🏫 São Paulo Tech School (SPTech)

---

# 📊 Status do Projeto

| Funcionalidade | Status |
|---|---|
| Estrutura em camadas | ✔ Concluído |
| CRUD de produtos | ✔ Concluído |
| Soft delete | ✔ Concluído |
| Validações | ✔ Concluído |
| Swagger | ✔ Concluído |
| Tratamento de exceções | ✔ Concluído |
| Testes unitários | ✔ Concluído |
| Documentação | ✔ Concluído |

---

# 📄 Licença

Projeto desenvolvido para fins acadêmicos e educacionais.
