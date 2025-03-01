package br.com.SpringBookstore.SpringBookstore.domain.enuns;

public enum ReservationStatus {
    PENDING,    // Reserva pendente.
    COMPLETED,  // Reserva concluída (usuário emprestou o livro).
    CANCELLED   // Reserva cancelada.
}
