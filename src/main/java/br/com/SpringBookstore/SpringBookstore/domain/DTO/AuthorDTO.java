package br.com.SpringBookstore.SpringBookstore.domain.DTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class AuthorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id; // Identificador único do autor.
    private String name; // Nome do autor.
    private String nacionalidade; // Nacionalidade do autor.
    private LocalDate dataNascimento; // Data de nascimento do autor.
    private String biografia; // Breve biografia do autor.

    private List<String> bookIds; // IDs dos livros escritos pelo autor.

    // Construtor padrão
    public AuthorDTO() {
    }

    // Construtor que recebe uma entidade Author
    public AuthorDTO(String id, String name, String nacionalidade, LocalDate dataNascimento,
                     String biografia, List<String> bookIds) {
        this.id = id;
        this.name = name;
        this.nacionalidade = nacionalidade;
        this.dataNascimento = dataNascimento;
        this.biografia = biografia;
        this.bookIds = bookIds;
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

    public List<String> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<String> bookIds) {
        this.bookIds = bookIds;
    }
}