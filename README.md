# Modelo de Sistema de Biblioteca

Este documento descreve as classes, atributos, métodos e relacionamentos de um sistema de biblioteca simplificado. A classe **Suporte Técnico** foi removida para focar nas entidades essenciais.

## **Classes Principais**
- **Livro**
- **Autor**
- **Usuário**
- **Funcionário**
- **Nível de Permissão**
- **Empréstimo**
- **Categoria**
- **Reserva**
- **Sistema**

## **Relacionamentos Revisitados**

### **1. Livro ↔ Autor**
- **Relacionamento:** Um livro pode ter um ou mais autores, e um autor pode escrever um ou mais livros.
- **Cardinalidade:** Muitos-para-Muitos *(N:N)*.
- **Implementação:** Tabela intermediária `LivroAutor`:
  ```
  livro_id
  autor_id
  ```

### **2. Usuário ↔ Empréstimo**
- **Relacionamento:** Um usuário pode ter vários empréstimos, mas cada empréstimo pertence a um único usuário.
- **Cardinalidade:** Um-para-Muitos *(1:N)*.
- **Implementação:** A classe `Emprestimo` possui uma chave estrangeira (`usuario_id`) que referencia a classe `Usuario`.

### **3. Livro ↔ Empréstimo**
- **Relacionamento:** Um livro pode estar associado a vários empréstimos (em diferentes momentos), mas cada empréstimo envolve um único livro.
- **Cardinalidade:** Um-para-Muitos *(1:N)*.
- **Implementação:** A classe `Emprestimo` possui uma chave estrangeira (`livro_id`) que referencia a classe `Livro`.

### **4. Funcionário ↔ Nível de Permissão**
- **Relacionamento:** Um funcionário tem um nível de permissão, e um nível de permissão pode ser atribuído a vários funcionários.
- **Cardinalidade:** Muitos-para-Um *(N:1)*.
- **Implementação:** A classe `Funcionario` possui uma chave estrangeira (`nivelPermissao_id`) que referencia a classe `NivelPermissao`.

### **5. Categoria ↔ Livro**
- **Relacionamento:** Uma categoria pode conter vários livros, e um livro pertence a uma única categoria.
- **Cardinalidade:** Um-para-Muitos *(1:N)*.
- **Implementação:** A classe `Livro` possui uma chave estrangeira (`categoria_id`) que referencia a classe `Categoria`.

### **6. Reserva ↔ Usuário e Livro**
- **Relacionamento:**
  - Um usuário pode fazer várias reservas.
  - Um livro pode ter várias reservas.
- **Cardinalidade:** Muitos-para-Muitos *(N:N)*.
- **Implementação:** Tabela intermediária `Reserva`:
  ```
  usuario_id
  livro_id
  dataReserva
  status
  ```

### **7. Sistema ↔ Outras Classes**
- **Relacionamento:** O sistema centraliza todas as operações e mantém listas de entidades como usuários, livros, funcionários, etc.
- **Cardinalidade:** Um-para-Muitos *(1:N)* para cada entidade.
- **Implementação:** O sistema não precisa de chaves estrangeiras, pois ele atua como um controlador central.

---


## **Class Diagram**
```plaintext
+------------------+       +------------------+       +------------------+
|      Book       |<>-----|    BookAuthor    |<>-----|      Author      |
+------------------+       +------------------+       +------------------+
| - id            |       | - book_id        |       | - id             |
| - title         |       | - author_id      |       | - name           |
| - isbn          |       +------------------+       | - nationality    |
| - publicationYear |                                | - birthDate      |
| - publisher     |                                | - biography      |
| - availableQuantity |                             +------------------+
| - category      |
| - status        |
+------------------+

+------------------+       +------------------+       +------------------+
|      User       |<>-----|      Loan       |<>-----|       Book       |
+------------------+       +------------------+       +------------------+
| - id            |       | - id             |       | - id             |
| - name          |       | - user_id        |       | - title          |
| - email         |       | - book_id        |       | - isbn           |
| - phone         |       | - loanDate       |       | - publicationYear |
| - address       |       | - expectedReturnDate |   | - publisher      |
| - registrationDate |     | - actualReturnDate  |   | - availableQuantity |
| - loanHistory   |       | - status         |       | - category       |
+------------------+       +------------------+       | - status        |
                                                  +------------------+

+------------------+       +------------------+       +------------------+
|   Employee      |<>-----| PermissionLevel |       |     Category     |
+------------------+       +------------------+       +------------------+
| - id            |       | - id             |       | - id             |
| - name          |       | - description    |       | - name           |
| - role          |       | - permissions    |       | - description    |
| - permissionLevel |      +------------------+       +------------------+
| - hireDate      |
+------------------+

+------------------+       +------------------+       +------------------+
|   Reservation   |<>-----|       Book       |<>-----|      User       |
+------------------+       +------------------+       +------------------+
| - id            |       | - id             |       | - id             |
| - user_id       |       | - title          |       | - name           |
| - book_id       |       | - isbn           |       | - email          |
| - reservationDate |      | - publicationYear |      | - phone         |
| - status        |       | - publisher      |       | - address        |
+------------------+       | - availableQuantity |    | - registrationDate |
                        | - category       |       +------------------+
                        | - status        |
                        +------------------+

+----------------+       +----------------+
|   Publisher   |<>-----|     Book       |
+----------------+       +----------------+
| - id          |       | - id           |
| - name        |       | - title        |
| - address     |       | - publisher_id |
+----------------+       +----------------+
