package br.com.SpringBookstore.SpringBookstore.domain.enuns;

public enum BookStatus {
    AVAILABLE, // Disponível para empréstimo.
    RESERVED,  // Reservado por um usuário.
    LOANED,    // Emprestado.
    DAMAGED,   // Danificado.
    LOST       // Perdido.
}
