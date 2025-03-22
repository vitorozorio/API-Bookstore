package br.com.SpringBookstore.SpringBookstore.domain.DTO;

import br.com.SpringBookstore.SpringBookstore.domain.enuns.ReservationStatus;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ReservationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id; // Identificador único da reserva.
    private LocalDateTime reservationDate; // Data e hora em que a reserva foi feita.
    private ReservationStatus status; // Status da reserva.

    private String userId; // ID do usuário que fez a reserva.
    private String bookId; // ID do livro reservado.

    // Construtor padrão
    public ReservationDTO() {
    }

    // Construtor que recebe uma entidade Reservation
    public ReservationDTO(String id, LocalDateTime reservationDate, ReservationStatus status, String userId, String bookId) {
        this.id = id;
        this.reservationDate = reservationDate;
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