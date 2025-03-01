package br.com.SpringBookstore.SpringBookstore.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Document(collection = "PermissionLevel")
public class PermissionLevel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id; // Identificador único do nível de permissão.
    private String description; // Descrição do nível de permissão (Ex.: Administrador).

    @DBRef
    private List<Employee> employees; // Lista de funcionários com este nível de permissão.

    // Construtores
    public PermissionLevel() {
    }

    public PermissionLevel(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}



