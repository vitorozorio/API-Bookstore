package br.com.SpringBookstore.SpringBookstore.repository;

import br.com.SpringBookstore.SpringBookstore.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    // Buscar por título ignorando maiúsculas e minúsculas
    @Query("{'title': {$regex: ?0, $options: 'i'}}")
    Optional<Book> findByTitle(String title);

    // Buscar por ISBN (esse pode continuar normal)
    Book findByIsbn(String isbn);
}
