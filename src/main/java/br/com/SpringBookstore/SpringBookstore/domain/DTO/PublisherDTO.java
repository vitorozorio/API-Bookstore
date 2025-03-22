package br.com.SpringBookstore.SpringBookstore.domain.DTO;

import java.io.Serializable;

public class PublisherDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id; // Identificador único da editora.
    private String name; // Nome da editora.
    private String endereco; // Endereço da editora.

    // Construtor padrão
    public PublisherDTO() {
    }

    // Construtor que recebe uma entidade Publisher
    public PublisherDTO(String id, String name, String endereco) {
        this.id = id;
        this.name = name;
        this.endereco = endereco;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}