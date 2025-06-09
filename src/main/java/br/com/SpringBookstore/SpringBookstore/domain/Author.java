package br.com.SpringBookstore.SpringBookstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import java.util.List;


@Entity
@Table(name = "Authors")
public class Author implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String nacionalidade;
    private LocalDate dataNascimento;
    private String biografia;


    @JsonIgnore
    @ManyToMany(mappedBy = "Books")
    private List<Book> books;

    public Author() {
    }

    public Author(String name, String nacionalidade, LocalDate dataNascimento, String biografia, List<Book> books) {
        this.name = name;
        this.nacionalidade = nacionalidade;
        this.dataNascimento = dataNascimento;
        this.biografia = biografia;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}