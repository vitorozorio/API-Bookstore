package br.com.SpringBookstore.SpringBookstore.exception;

import java.util.Date;

/**
 * Classe para estruturar a resposta de erros para o cliente da API.
 */
public record ErrorResponse(Date timestamp, String message, String details) {}




