package br.com.SpringBookstore.SpringBookstore.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Document(collection="Reservation")
public class Reservation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;


    private User user;
    private Book book;
    private Date reservationDate;

    // status enum


}
