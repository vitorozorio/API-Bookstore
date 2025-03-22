package br.com.SpringBookstore.SpringBookstore.domain.DTO;

import br.com.SpringBookstore.SpringBookstore.domain.enuns.LoanStatus;
import java.io.Serializable;
import java.time.LocalDateTime;

public class LoanDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id; // Identificador único do empréstimo.
    private LocalDateTime loanDate; // Data e hora em que o empréstimo foi realizado.
    private LocalDateTime expectedReturnDate; // Data e hora esperada para a devolução.
    private LocalDateTime actualReturnDate; // Data e hora real da devolução (opcional).
    private LoanStatus status; // Status do empréstimo.

    private String userId; // ID do usuário que realizou o empréstimo.
    private String bookId; // ID do livro relacionado ao empréstimo.

    // Construtor padrão
    public LoanDTO() {
    }

    // Construtor que recebe uma entidade Loan
    public LoanDTO(String id, LocalDateTime loanDate, LocalDateTime expectedReturnDate,
                   LocalDateTime actualReturnDate, LoanStatus status, String userId, String bookId) {
        this.id = id;
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
        this.status = status;
        this.userId = userId;
        this.bookId = bookId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}