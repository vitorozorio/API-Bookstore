package br.com.SpringBookstore.SpringBookstore.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Document(collection = "Category")
public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private String name; // name da categoria, ex: Ficção, Fantasia, Drama

    private String descricao; // Breve descrição ou detalhes adicionais da categoria

    @DBRef
    private List<Book> books; // Relacionamento muitos-para-muitos com Livros

    public Category(Integer id, String name, String descricao, List<Book> books) {
        this.id = id;
        this.name = name;
        this.descricao = descricao;
        this.books = books;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}