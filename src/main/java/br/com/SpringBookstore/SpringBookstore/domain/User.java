package br.com.SpringBookstore.SpringBookstore.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Document(collection = "User") // Indica que esta classe representa uma coleção no MongoDB.
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id; // Identificador único do usuário.
    private String name; // Nome do usuário.
    private String email; // Email do usuário.
    private String phone; // Telefone do usuário.
    private String address; // Endereço do usuário.

    @DBRef
    private List<Loan> loans; // Lista de empréstimos associados ao usuário.

    @DBRef
    private List<Reservation> reservations; // Lista de reservas feitas pelo usuário.

    // Construtores
    public User() {
    }

    public User(Integer id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}