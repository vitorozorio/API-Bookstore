package br.com.SpringBookstore.SpringBookstore.domain.DTO;

import java.io.Serializable;

public class ReviewDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id; // Identificador único da avaliação.
    private String comment; // Comentário deixado pelo usuário.
    private Integer rating; // Nota dada ao livro (de 1 a 5).

    private String userId; // ID do usuário que fez a avaliação.
    private String bookId; // ID do livro avaliado.

    // Construtor padrão
    public ReviewDTO() {
    }

    // Construtor que recebe uma entidade Review
    public ReviewDTO(String id, String comment, Integer rating, String userId, String bookId) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
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