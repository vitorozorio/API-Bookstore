package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.Category;
import br.com.SpringBookstore.SpringBookstore.exception.BusinessLogicException;
import br.com.SpringBookstore.SpringBookstore.exception.ConflictException;
import br.com.SpringBookstore.SpringBookstore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    // Buscar todas as categorias
    public List<Category> findAll() {
        return repository.findAll();
    }

    // Buscar uma categoria pelo ID
    public Category findById(String id) { // Alterado de Integer para String
        Optional<Category> obj = repository.findById(id);
        if (obj.isEmpty()) {
            throw new BusinessLogicException("Categoria não encontrada com o ID: " + id);
        }
        return obj.get();
    }

    // Criar uma nova categoria
    public Category insert(Category category) {
        // Validar se o nome da categoria já existe
        if (repository.findByName(category.getName()) != null) {
            throw new ConflictException("Categoria já cadastrada: " + category.getName());
        }
        return repository.save(category);
    }

    // Atualizar uma categoria existente
    public Category update(String id, Category updatedCategory) { // Alterado de Integer para String
        Category entity = findById(id);

        // Validar se o novo nome está sendo alterado para um nome já existente
        if (!entity.getName().equals(updatedCategory.getName()) && repository.findByName(updatedCategory.getName()) != null) {
            throw new ConflictException("Categoria já cadastrada: " + updatedCategory.getName());
        }

        // Atualizar os dados
        entity.setName(updatedCategory.getName());
        entity.setDescricao(updatedCategory.getDescricao());
        return repository.save(entity);
    }

    // Deletar uma categoria pelo ID
    public void delete(String id) { // Alterado de Integer para String
        Category category = findById(id); // Verifica se existe antes de deletar
        repository.deleteById(id);
    }
}