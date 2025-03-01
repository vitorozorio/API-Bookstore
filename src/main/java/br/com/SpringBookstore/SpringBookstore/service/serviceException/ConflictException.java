package br.com.SpringBookstore.SpringBookstore.service.serviceException;

/**
 * Exceção lançada quando há um conflito no estado dos dados.
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
