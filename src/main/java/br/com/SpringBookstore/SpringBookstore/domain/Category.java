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

    private String nome; // Nome da categoria, ex: Ficção, Fantasia, Drama

    private String descricao; // Breve descrição ou detalhes adicionais da categoria

    @DBRef
    private List<Book> books; // Relacionamento muitos-para-muitos com Livros

    public Category(Integer id, String nome, String descricao, List<Book> books) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.books = books;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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