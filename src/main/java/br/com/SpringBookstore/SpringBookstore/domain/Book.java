package br.com.SpringBookstore.SpringBookstore.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Document(collection="Book")
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    private String title;
    private Date yearPublication;
    private Integer quantityAvailable;
    private Author author;

    // categoria

    // editora nova classe

    // bookStatus enum

}
