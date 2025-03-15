package br.com.SpringBookstore.SpringBookstore.controller;

import br.com.SpringBookstore.SpringBookstore.domain.Author;
import br.com.SpringBookstore.SpringBookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    // Buscar todos os autores
    @GetMapping
    public ResponseEntity<List<Author>> findAll() {
        List<Author> authors = authorService.findAll();
        return ResponseEntity.ok(authors);
    }

    // Buscar um autor pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Author> findById(@PathVariable Integer id) {
        Author author = authorService.findById(id);
        return ResponseEntity.ok(author);
    }

    // Criar um novo autor
    @PostMapping
    public ResponseEntity<Author> insert(@RequestBody Author author) {
        Author newAuthor = authorService.insert(author);
        return ResponseEntity.status(201).body(newAuthor);
    }

    // Atualizar um autor existente
    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable Integer id, @RequestBody Author updatedAuthor) {
        Author updated = authorService.update(id, updatedAuthor);
        return ResponseEntity.ok(updated);
    }

    // Deletar um autor pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}