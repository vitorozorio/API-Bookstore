package br.com.SpringBookstore.SpringBookstore.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Document(collection = "Review")
public class Review implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id; // Identificador único da avaliação.

    @Size(max = 1000, message = "O comentário deve ter no máximo 1000 caracteres.")
    private String comment; // Comentário deixado pelo usuário (opcional).

    @Min(value = 1, message = "A nota deve ser no mínimo 1.")
    @Max(value = 5, message = "A nota deve ser no máximo 5.")
    private Integer rating; // Nota dada ao livro (de 1 a 5).

    @DBRef
    private User user; // Usuário que fez a avaliação.

    @DBRef
    private Book book; // Livro avaliado.

    // Construtor vazio (necessário para frameworks como Spring Data)
    public Review() {
    }

    // Construtor com parâmetros (sem o id, pois será gerado automaticamente)
    public Review(String comment, Integer rating, User user, Book book) {
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.book = book;
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

    // Método para verificar se a avaliação é positiva (nota >= 4)
    public boolean isPositive() {
        return rating != null && rating >= 4;
    }

    // Método para verificar se a avaliação é negativa (nota <= 2)
    public boolean isNegative() {
        return rating != null && rating <= 2;
    }
}