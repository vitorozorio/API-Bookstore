package br.com.SpringBookstore.SpringBookstore.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serial;
import java.io.Serializable;

@Document(collection = "Review")
public class Review implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id; // Identificador único da avaliação.
    private String comment; // Comentário deixado pelo usuário.
    private Integer rating; // Nota dada ao livro (de 1 a 5, por exemplo).

    @DBRef
    private User user; // Usuário que fez a avaliação.

    @DBRef
    private Book book; // Livro avaliado.

    // Construtores
    public Review() {
    }

    public Review(Integer id, String comment, Integer rating, User user, Book book) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.book = book;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}