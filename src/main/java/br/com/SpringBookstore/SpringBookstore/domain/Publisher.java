package br.com.SpringBookstore.SpringBookstore.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Document(collection = "Publisher")
public class Publisher implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private String name;
    private String endereco;

    public Publisher(Integer id, String name, String endereco) {
        this.id = id;
        this.name = name;
        this.endereco = endereco;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}