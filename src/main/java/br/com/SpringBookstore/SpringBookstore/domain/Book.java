package br.com.SpringBookstore.SpringBookstore.domain;

import br.com.SpringBookstore.SpringBookstore.domain.enuns.BookStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Books")
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String isbn;
    private Integer publicationYear;
    private String publisher;
    private Integer availableQuantity;
    private BookStatus status;

    @JsonIgnore
    @ManyToMany(mappedBy = "Categories")
    private Category category;

    @JsonIgnore
    @ManyToMany(mappedBy = "Authors")
    private List<Author> authors;

    @JsonIgnore
    @ManyToMany(mappedBy = "Reviews")
    private List<Review> reviews;

    public Book() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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