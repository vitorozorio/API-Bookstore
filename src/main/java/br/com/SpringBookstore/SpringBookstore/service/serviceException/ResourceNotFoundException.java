package br.com.SpringBookstore.SpringBookstore.service.serviceException;

/**
 * Exceção para quando um recurso não for encontrado no sistema.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
