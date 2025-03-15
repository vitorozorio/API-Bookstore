package br.com.SpringBookstore.SpringBookstore.repository;

import br.com.SpringBookstore.SpringBookstore.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends MongoRepository<Author, Integer> {
    // Queries personalizadas podem ser adicionadas aqui
    Author findByName(String name); // Exemplo de busca por nome
}
