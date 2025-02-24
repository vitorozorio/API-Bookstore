package br.com.SpringBookstore.SpringBookstore.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serial;

@Document(collection="Author")
public class Loan {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
}
