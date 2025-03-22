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
    private String id; // Identificador único da reserva.

    @NotNull(message = "A data da reserva é obrigatória.")
    private LocalDateTime reservationDate; // Data e hora em que a reserva foi feita.

    @NotNull(message = "O status da reserva é obrigatório.")
    private ReservationStatus status; // Status da reserva (ex.: PENDING, CONFIRMED, CANCELLED).

    @DBRef
    private User user; // Usuário que fez a reserva.

    @DBRef
    private Book book; // Livro reservado.

    // Construtor vazio (necessário para frameworks como Spring Data)
    public Reservation() {
    }

    // Construtor com parâmetros (sem o id, pois será gerado automaticamente)
    public Reservation(LocalDateTime reservationDate, ReservationStatus status, User user, Book book) {
        this.reservationDate = reservationDate;
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

    // Método para verificar se a reserva está ativa
    public boolean isActive() {
        return ReservationStatus.PENDING.equals(status) || ReservationStatus.CONFIRMED.equals(status);
    }

    // Método para verificar se a reserva está expirada
    public boolean isExpired() {
        return ReservationStatus.CANCELLED.equals(status) || LocalDateTime.now().isAfter(reservationDate.plusDays(7));
    }
}