package br.com.SpringBookstore.SpringBookstore.service.serviceException;

/**
 * Exceção genérica para erros de lógica de negócios.
 */
public class BusinessLogicException extends RuntimeException {
    public BusinessLogicException(String message) {
        super(message);
    }
}
