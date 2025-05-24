package br.com.SpringBookstore.SpringBookstore.domain;

import br.com.SpringBookstore.SpringBookstore.domain.enuns.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "Reservation")
public class Reservation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private LocalDateTime reservationDate;
    private ReservationStatus status;

    @DBRef
    private User user;

    @DBRef
    private Book book;

    public Reservation() {
    }

    public Reservation(LocalDateTime reservationDate, ReservationStatus status, User user, Book book) {
        this.reservationDate = reservationDate;
        this.status = status;
        this.user = user;
        this.book = book;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
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