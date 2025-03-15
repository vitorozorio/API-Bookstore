package br.com.SpringBookstore.SpringBookstore.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Document(collection = "categories") // Nome da coleção no MongoDB
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id; // Alterado de Integer para String

    private String name; // Nome da categoria, ex: Ficção, Fantasia, Drama

    private String descricao; // Breve descrição ou detalhes adicionais da categoria

    // Construtor vazio (necessário para frameworks como Spring Data)
    public Category() {}

    // Construtor com parâmetros (removido o id, pois será gerado automaticamente pelo MongoDB)
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