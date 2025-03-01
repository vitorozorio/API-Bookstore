package br.com.SpringBookstore.SpringBookstore.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serial;
import java.io.Serializable;

@Document(collection = "Employee")
public class Employee implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id; // Identificador único do funcionário.
    private String name; // Nome do funcionário.
    private String position; // Cargo do funcionário (Ex.: Bibliotecário, Gerente).

    @DBRef
    private PermissionLevel permissionLevel; // Relação com o nível de permissão.

    // Construtores
    public Employee() {
    }

    public Employee(Integer id, String name, String position, PermissionLevel permissionLevel) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.permissionLevel = permissionLevel;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }
}