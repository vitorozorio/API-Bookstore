package br.com.SpringBookstore.SpringBookstore.domain.DTO;

import java.io.Serializable;

public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id; // Identificador único da categoria.
    private String name; // Nome da categoria.
    private String descricao; // Breve descrição ou detalhes adicionais da categoria.

    // Construtor padrão
    public CategoryDTO() {
    }

    // Construtor que recebe uma entidade Category
    public CategoryDTO(String id, String name, String descricao) {
        this.id = id;
        this.name = name;
        this.descricao = descricao;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}