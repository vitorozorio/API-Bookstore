package br.com.SpringBookstore.SpringBookstore.domain;

import br.com.SpringBookstore.SpringBookstore.domain.enuns.BookStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Document(collection = "Book")
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id; // Identificador único do livro.

    @NotBlank(message = "O título do livro é obrigatório.")
    private String title; // Título do livro.

    @NotBlank(message = "O ISBN do livro é obrigatório.")
    private String isbn; // Código ISBN.

    @NotNull(message = "O ano de publicação é obrigatório.")
    @Min(value = 0, message = "O ano de publicação deve ser maior ou igual a zero.")
    private Integer publicationYear; // Ano de publicação.

    @NotBlank(message = "A editora do livro é obrigatória.")
    private String publisher; // Editora.

    @NotNull(message = "A quantidade disponível é obrigatória.")
    @Min(value = 0, message = "A quantidade disponível deve ser maior ou igual a zero.")
    private Integer availableQuantity; // Quantidade disponível.

    @NotNull(message = "O status do livro é obrigatório.")
    private BookStatus status; // Status atual do livro.

    @DBRef
    private Category category; // Categoria do livro.

    @DBRef
    private List<Author> authors; // Autores do livro.

    @DBRef
    private List<Review> reviews; // Lista de avaliações/feedback feitas por usuários.

    // Construtor vazio (necessário para frameworks como Spring Data)
    public Book() {
    }

    // Construtor com parâmetros (sem o id, pois será gerado automaticamente)
    public Book(String title, String isbn, Integer publicationYear, String publisher,
                Integer availableQuantity, BookStatus status, Category category, List<Author> authors, List<Review> reviews) {
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
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