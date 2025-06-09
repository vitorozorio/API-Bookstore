package br.com.SpringBookstore.SpringBookstore.domain;

import br.com.SpringBookstore.SpringBookstore.domain.enuns.LoanStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Loans")
public class Loan implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime loanDate;
    private LocalDateTime expectedReturnDate;
    private LocalDateTime actualReturnDate;
    private LoanStatus status;

    @JsonIgnore
    @ManyToMany(mappedBy = "Users")
    private User user;

    @JsonIgnore
    @ManyToMany(mappedBy = "Books")
    private Book book;

    public Loan() {
    }

    public Loan(LocalDateTime loanDate, LocalDateTime expectedReturnDate, LoanStatus status, User user, Book book) {
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
        this.status = status;
        this.user = user;
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

}