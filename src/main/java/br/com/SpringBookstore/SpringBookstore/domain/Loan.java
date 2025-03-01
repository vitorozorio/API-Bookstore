package br.com.SpringBookstore.SpringBookstore.domain;

import br.com.SpringBookstore.SpringBookstore.domain.enuns.LoanStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Document(collection= "Loan")
public class Loan implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id; // Identificador único do empréstimo.
    private Date loanDate; // Data em que o empréstimo foi feito.
    private Date expectedReturnDate; // Data de devolução esperada.
    private Date actualReturnDate; // Data em que a devolução realmente ocorreu.
    private LoanStatus status; // Enum que define o status do empréstimo.

    @DBRef
    private User user; // Usuário que realizou o empréstimo.

    @DBRef
    private Book book; // Livro relacionado ao empréstimo.

    // Construtores
    public Loan() {
    }

    public Loan(Integer id, Date loanDate, Date expectedReturnDate, LoanStatus status, User user, Book book) {
        this.id = id;
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
        this.status = status;
        this.user = user;
        this.book = book;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(Date expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public Date getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(Date actualReturnDate) {
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
}
