package br.com.SpringBookstore.SpringBookstore.domain;

import br.com.SpringBookstore.SpringBookstore.domain.enuns.ReservationStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Document(collection = "Reservation")
public class Reservation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id; // Identificador único da reserva.
    private Date reservationDate; // Data em que a reserva foi feita.
    private ReservationStatus status; // Enum que define o status da reserva.

    @DBRef
    private User user; // Usuário que fez a reserva.

    @DBRef
    private Book book; // Livro reservado.

    // Construtores
    public Reservation() {
    }

    public Reservation(Integer id, Date reservationDate, ReservationStatus status, User user, Book book) {
        this.id = id;
        this.reservationDate = reservationDate;
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

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
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
