package br.com.Spring_Bookstore.Spring_Bookstore.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection="Person")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String userName;
    private String password;
    private String email;
    private Integer level;

    @DBRef(lazy = true)
    private List<Book> books = new ArrayList<>();





}
