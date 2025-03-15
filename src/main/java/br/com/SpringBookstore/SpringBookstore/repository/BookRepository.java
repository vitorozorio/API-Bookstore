package br.com.SpringBookstore.SpringBookstore.repository;

import br.com.SpringBookstore.SpringBookstore.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, Integer> {
    // Consulta personalizada para buscar um livro pelo título
    Book findByTitle(String title);

    // Consulta personalizada para buscar um livro pelo ISBN
    Book findByIsbn(String isbn);
}