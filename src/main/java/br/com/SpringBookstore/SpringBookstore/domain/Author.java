package br.com.SpringBookstore.SpringBookstore.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "Author")
public class Author implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id; // Identificador único do autor.

    @NotBlank(message = "O nome do autor é obrigatório.")
    private String name; // Nome do autor.

    @NotBlank(message = "A nacionalidade do autor é obrigatória.")
    private String nacionalidade; // Nacionalidade do autor.

    @NotNull(message = "A data de nascimento do autor é obrigatória.")
    private LocalDate dataNascimento; // Data de nascimento do autor.

    private String biografia; // Breve biografia do autor.

    @DBRef
    private List<Book> books; // Livros escritos pelo autor.

    // Construtor vazio (necessário para frameworks como Spring Data)
    public Author() {
    }

    // Construtor com parâmetros (sem o id, pois será gerado automaticamente)
    public Author(String name, String nacionalidade, LocalDate dataNascimento, String biografia, List<Book> books) {
        this.name = name;
        this.nacionalidade = nacionalidade;
        this.dataNascimento = dataNascimento;
        this.biografia = biografia;
        this.books = books;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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