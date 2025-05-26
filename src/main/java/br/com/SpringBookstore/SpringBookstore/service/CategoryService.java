package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.Category;
import br.com.SpringBookstore.SpringBookstore.exception.BusinessLogicException;
import br.com.SpringBookstore.SpringBookstore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Categoria não encontrada com o ID: " + id));
    }

    public Category insert(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(String id, Category category) {
         categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Categoria não encontrada com o ID: " + id));

        category.setName(category.getName());
        category.setDescricao(category.getDescricao());

        return  categoryRepository.save(category);
    }

    public void delete(String id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Categoria não encontrada com o ID: " + id));
        categoryRepository.deleteById(id);
    }
}
