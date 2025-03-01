package br.com.SpringBookstore.SpringBookstore.domain;

import br.com.SpringBookstore.SpringBookstore.domain.enuns.BookStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Document(collection = "Book")
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id; // Identificador único do livro.
    private String title; // Título do livro.
    private String isbn; // Código ISBN.
    private Integer publicationYear; // Ano de publicação.
    private String publisher; // Editora.
    private Integer availableQuantity; // Quantidade disponível.
    private BookStatus status; // Enum que define o status atual do livro.

    @DBRef
    private Category category; // Categoria do livro.

    @DBRef
    private List<Author> authors; // Autores do livro.

    @DBRef
    private List<Review> reviews; // Lista de avaliações/feedback feitas por usuários.

    // Construtores
    public Book() {
    }

    public Book(Integer id, String title, String isbn, Integer publicationYear, String publisher, Integer availableQuantity, BookStatus status, Category category, List<Author> authors, List<Review> reviews) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.availableQuantity = availableQuantity;
        this.status = status;
        this.category = category;
        this.authors = authors;
        this.reviews = reviews;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}