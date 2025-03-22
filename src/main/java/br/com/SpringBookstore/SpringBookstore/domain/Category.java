package br.com.SpringBookstore.SpringBookstore.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Document(collection = "categories")
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id; // Identificador único da categoria.

    @NotBlank(message = "O nome da categoria é obrigatório.")
    @Size(max = 100, message = "O nome da categoria deve ter no máximo 100 caracteres.")
    private String name; // Nome da categoria (ex.: Ficção, Fantasia, Drama).

    @Size(max = 500, message = "A descrição da categoria deve ter no máximo 500 caracteres.")
    private String descricao; // Breve descrição ou detalhes adicionais da categoria.

    // Construtor vazio (necessário para frameworks como Spring Data)
    public Category() {
    }

    // Construtor com parâmetros (sem o id, pois será gerado automaticamente pelo MongoDB)
    public Category(String name, String descricao) {
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