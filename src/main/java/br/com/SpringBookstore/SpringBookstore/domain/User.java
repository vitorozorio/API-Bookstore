package br.com.SpringBookstore.SpringBookstore.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Document(collection="Person")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String userName;
    private String email;
    private String telefone;
    private String endereco;

    private Date dataCadastro;

    // list -- historicoEmprestimos


}
