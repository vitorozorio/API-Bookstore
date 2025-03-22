package br.com.SpringBookstore.SpringBookstore.domain;

import br.com.SpringBookstore.SpringBookstore.domain.enuns.LoanStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "Loan")
public class Loan implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id; // Identificador único do empréstimo.

    @NotNull(message = "A data do empréstimo é obrigatória.")
    private LocalDateTime loanDate; // Data e hora em que o empréstimo foi realizado.

    @NotNull(message = "A data de devolução esperada é obrigatória.")
    private LocalDateTime expectedReturnDate; // Data e hora esperada para a devolução.

    private LocalDateTime actualReturnDate; // Data e hora real da devolução (opcional).

    @NotNull(message = "O status do empréstimo é obrigatório.")
    private LoanStatus status; // Status do empréstimo (ex.: ACTIVE, RETURNED).

    @DBRef
    private User user; // Usuário que realizou o empréstimo.

    @DBRef
    private Book book; // Livro relacionado ao empréstimo.

    // Construtor vazio (necessário para frameworks como Spring Data)
    public Loan() {
    }

    // Construtor com parâmetros (sem o id, pois será gerado automaticamente)
    public Loan(LocalDateTime loanDate, LocalDateTime expectedReturnDate, LoanStatus status, User user, Book book) {
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
        this.status = status;
        this.user = user;
        this.book = book;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDateTime getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(LocalDateTime expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public LocalDateTime getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDateTime actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    // Método para verificar se o empréstimo está atrasado
    public boolean isOverdue() {
        return LocalDateTime.now().isAfter(expectedReturnDate) && LoanStatus.ACTIVE.equals(status);
    }

    // Método para verificar se o empréstimo foi devolvido
    public boolean isReturned() {
        return LoanStatus.RETURNED.equals(status);
    }
}