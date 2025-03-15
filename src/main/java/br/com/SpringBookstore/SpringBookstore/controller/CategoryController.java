package br.com.SpringBookstore.SpringBookstore.controller;

import br.com.SpringBookstore.SpringBookstore.domain.Category;
import br.com.SpringBookstore.SpringBookstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Buscar todas as categorias
    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    // Buscar uma categoria pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Integer id) {
        Category category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    // Criar uma nova categoria
    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody Category category) {
        Category newCategory = categoryService.insert(category);
        return ResponseEntity.status(201).body(newCategory);
    }

    // Atualizar uma categoria existente
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Integer id, @RequestBody Category updatedCategory) {
        Category updated = categoryService.update(id, updatedCategory);
        return ResponseEntity.ok(updated);
    }

    // Deletar uma categoria pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}