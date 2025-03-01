# **Documentação do Sistema de Biblioteca**

Este documento apresenta o modelo de dados do Sistema de Gerenciamento de Biblioteca. Ele inclui entidades principais, como livros, autores, usuários, avaliações, empréstimos e reservas, e explica como elas interagem entre si.

---

## **Entidades Principais e Relacionamentos**

### **1. Book (Livro)**

- **Descrição**: Representa um livro na biblioteca.
- **Relacionamentos**:
  - **Autores**: Relação muitos-para-muitos com `Author`.
  - **Avaliações**: Uma lista de `Review`.
  - **Empréstimos**: Associado a `Loan`.
  - **Reservas**: Associado a `Reservation`.
  - **Categoria**: Associação (1 para N) com `Category`.

#### **Atributos**:
- `id`: Identificador único do livro.
- `title`: Título do livro.
- `isbn`: Código ISBN.
- `publicationYear`: Ano de publicação.
- `publisher`: Nome da editora.
- `availableQuantity`: Quantidade disponível para empréstimos.
- `status`: Status do livro (`AVAILABLE`, `RESERVED`, `LOANED`, `DAMAGED`, `LOST`).
- **Referências**:
  - `authors` → Lista de autores ligados ao livro.
  - `reviews` → Lista de avaliações.
  - `category` → Referência à categoria do livro.

---

### **2. Author (Autor)**

- **Descrição**: Representa os autores dos livros.
- **Relacionamentos**:
  - Um autor pode escrever vários livros (relação muitos-para-muitos com `Book`).

#### **Atributos**:
- `id`: Identificador único do autor.
- `name`: Nome do autor.
- `nationality`: Nacionalidade.
- `biography`: Biografia do autor (opcional).
- `books`: Lista de livros escritos pelo autor.

---

### **3. Category (Categoria)**

- **Descrição**: Representa uma categoria ou gênero de livro, como Ficção, Não Ficção, etc.
- **Relacionamentos**:
  - Uma categoria pode conter vários livros.

#### **Atributos**:
- `id`: Identificador único.
- `name`: Nome da categoria.

---

### **4. Review (Avaliação)**

- **Descrição**: Representa a avaliação de um livro feita por um usuário.
- **Relacionamentos**:
  - `user` → Usuário que fez a avaliação.
  - `book` → Livro avaliado.

#### **Atributos**:
- `id`: Identificador único da avaliação.
- `comment`: Comentário sobre o livro.
- `rating`: Nota do livro (`1` a `5`).

---

### **5. Loan (Empréstimo)**

- **Descrição**: Representa o empréstimo de um livro por um usuário.
- **Relacionamentos**:
  - `user` → Usuário que solicitou o empréstimo.
  - `book` → Livro emprestado.

#### **Atributos**:
- `id`: Identificador único.
- `loanDate`: Data do empréstimo.
- `expectedReturnDate`: Data esperada para devolução.
- `actualReturnDate`: Data de devolução (se devolvido).
- `status`: Status do empréstimo (`ACTIVE`, `RETURNED`, `OVERDUE`).

---

### **6. Reservation (Reserva)**

- **Descrição**: Representa a reserva de um livro feita por um usuário.
- **Relacionamentos**:
  - `user` → Usuário que realizou a reserva.
  - `book` → Livro reservado.

#### **Atributos**:
- `id`: Identificador único.
- `reservationDate`: Data da reserva.
- `status`: Status da reserva (`PENDING`, `COMPLETED`, `CANCELLED`).

---

### **7. User (Usuário)**

- **Descrição**: Representa os usuários do sistema, como leitores, que interagem com os livros.
- **Relacionamentos**:
  - `loans` → Lista de empréstimos (`Loan`).
  - `reservations` → Lista de reservas (`Reservation`).
  - `reviews` → Lista de avaliações (`Review`).

#### **Atributos**:
- `id`: Identificador único do usuário.
- `name`: Nome do usuário.
- `email`: E-mail do usuário.
- `phoneNumber`: Número de telefone.
- `address`: Endereço físico.

---

### **8. Employee (Funcionário)**

- **Descrição**: Representa os funcionários que administram o sistema (ex.: bibliotecários).

#### **Atributos**:
- `id`: Identificador único.
- `name`: Nome do funcionário.
- `email`: E-mail do funcionário.
- `role`: Cargo ou função do funcionário.

---

## **Enums no Sistema**

### **1. BookStatus**
Definição do estado atual do livro:
- `AVAILABLE`: Disponível para empréstimos.
- `RESERVED`: Reservado.
- `LOANED`: Emprestado.
- `DAMAGED`: Danificado.
- `LOST`: Perdido.

### **2. LoanStatus**
Status do empréstimo:
- `ACTIVE`: Empréstimo ativo.
- `RETURNED`: Livro devolvido.
- `OVERDUE`: Empréstimo atrasado.

### **3. ReservationStatus**
Status de uma reserva:
- `PENDING`: Reserva pendente.
- `COMPLETED`: Reserva concluída.
- `CANCELLED`: Reserva cancelada.

### **4. Rating**
Notas de avaliação do livro:
- `ONE_STAR` (1 estrela).
- `TWO_STARS` (2 estrelas).
- `THREE_STARS` (3 estrelas).
- `FOUR_STARS` (4 estrelas).
- `FIVE_STARS` (5 estrelas).

---

## **Relações entre Entidades**

Diagrama simplificado das relações:

- **Book** ↔ **Author** (muitos para muitos)
- **Book** ↔ **Review** ↔ **User**
- **Book** ↔ **Loan** ↔ **User**
- **Book** ↔ **Reservation** ↔ **User**
- **Book** → **Category**

---

## **Exemplo de Interação no Sistema**

1. Um **usuário** realiza uma **reserva** de um livro disponível.
2. Um **funcionário** converte a reserva em um **empréstimo** ativo.
3. O usuário devolve o livro, alterando o status do empréstimo para **`RETURNED`**.
4. O usuário avalia o livro, criando uma nova **`Review`** com uma nota (`FIVE_STARS`).
5. O funcionário atualiza a quantidade disponível e status do livro, se necessário.

---

## **Conclusão**

O modelo do sistema foi projetado para ser eficiente e flexível, permitindo a interação entre diferentes entidades e suportando os principais processos de gerenciamento de biblioteca. Caso sejam necessárias informações adicionais ou exemplos de código, sinta-se à vontade para solicitar.