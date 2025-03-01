package br.com.SpringBookstore.SpringBookstore.exception;

import java.util.Date;

/**
 * Classe para estruturar a resposta de erros para o cliente da API.
 */
public class ErrorResponse {

    private Date timestamp;    // Data/hora do erro
    private int status;        // Código HTTP do erro
    private String error;      // Tipo do erro (ex.: "Bad Request")
    private String message;    // Mensagem de descrição do erro
    private String path;       // URL que causou o erro

    // Construtor
    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = new Date(); // Preenche automaticamente com a data atual
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Getters e Setters
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
