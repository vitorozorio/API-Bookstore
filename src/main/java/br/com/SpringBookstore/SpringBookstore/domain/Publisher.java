package br.com.SpringBookstore.SpringBookstore.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Document(collection = "Publisher")
public class Publisher implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id; // Identificador único da editora.

    @NotBlank(message = "O nome da editora é obrigatório.")
    @Size(max = 100, message = "O nome da editora deve ter no máximo 100 caracteres.")
    private String name; // Nome da editora.

    @Size(max = 500, message = "O endereço da editora deve ter no máximo 500 caracteres.")
    private String endereco; // Endereço da editora.

    // Construtor vazio (necessário para frameworks como Spring Data)
    public Publisher() {
    }

    // Construtor com parâmetros (sem o id, pois será gerado automaticamente pelo MongoDB)
    public Publisher(String name, String endereco) {
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