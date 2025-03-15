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
    public Category findById(Integer id) {
        Optional<Category> obj = repository.findById(id);
        if (obj.isEmpty()) {
            throw new BusinessLogicException("Categoria não encontrada com o ID: " + id);
        }
        return obj.get();
    }

    // Criar uma nova categoria
    public Category insert(Category category) {
        // Validar se o nome da categoria já existe
        if (repository.findByName(category.getname()) != null) {
            throw new ConflictException("Categoria já cadastrada: " + category.getname());
        }
        return repository.save(category);
    }

    // Atualizar uma categoria existente
    public Category update(Integer id, Category updatedCategory) {
        Category entity = findById(id);

        // Validar se o novo nome está sendo alterado para um nome já existente
        if (!entity.getname().equals(updatedCategory.getname()) && repository.findByName(updatedCategory.getname()) != null) {
            throw new ConflictException("Categoria já cadastrada: " + updatedCategory.getname());
        }

        updateData(entity, updatedCategory);
        return repository.save(entity);
    }

    // Deletar uma categoria pelo ID
    public void delete(Integer id) {
        Category category = findById(id); // Verifica se existe antes de deletar
        repository.deleteById(id);
    }

    // Método auxiliar para atualizar os dados da categoria
    private void updateData(Category entity, Category updatedCategory) {
        entity.setname(updatedCategory.getname());
        entity.setDescricao(updatedCategory.getDescricao());
        entity.setBooks(updatedCategory.getBooks());
    }
}