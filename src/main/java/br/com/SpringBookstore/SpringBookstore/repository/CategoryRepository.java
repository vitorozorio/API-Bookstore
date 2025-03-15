package br.com.SpringBookstore.SpringBookstore.repository;

import br.com.SpringBookstore.SpringBookstore.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, Integer> {
    Category findByName(String name);
}