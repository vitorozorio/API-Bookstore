package br.com.SpringBookstore.SpringBookstore.domain.DTO;

import br.com.SpringBookstore.SpringBookstore.domain.enuns.BookStatus;
import java.io.Serializable;
import java.util.List;

public class BookDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id; // Identificador único do livro.
    private String title; // Título do livro.
    private String isbn; // Código ISBN.
    private Integer publicationYear; // Ano de publicação.
    private String publisher; // Editora.
    private Integer availableQuantity; // Quantidade disponível.
    private BookStatus status; // Status atual do livro.
    private String categoryId; // ID da categoria do livro.

    private List<String> authorIds; // IDs dos autores do livro.
    private List<String> reviewIds; // IDs das avaliações do livro.

    // Construtor padrão
    public BookDTO() {
    }

    // Construtor que recebe uma entidade Book
    public BookDTO(String id, String title, String isbn, Integer publicationYear, String publisher,
                   Integer availableQuantity, BookStatus status, String categoryId, List<String> authorIds, List<String> reviewIds) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.availableQuantity = availableQuantity;
        this.status = status;
        this.categoryId = categoryId;
        this.authorIds = authorIds;
        this.reviewIds = reviewIds;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<String> authorIds) {
        this.authorIds = authorIds;
    }

    public List<String> getReviewIds() {
        return reviewIds;
    }

    public void setReviewIds(List<String> reviewIds) {
        this.reviewIds = reviewIds;
    }
}