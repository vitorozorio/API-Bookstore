package br.com.SpringBookstore.SpringBookstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;


import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "Reviews")
public class Review implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String comment;
    private Integer rating;

    @JsonIgnore
    @ManyToMany(mappedBy = "Users")
    private User user;

    @JsonIgnore
    @ManyToMany(mappedBy = "Books")
    private Book book;

    public Review() {
    }

    public Review(String comment, Integer rating, User user, Book book) {
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.book = book;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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