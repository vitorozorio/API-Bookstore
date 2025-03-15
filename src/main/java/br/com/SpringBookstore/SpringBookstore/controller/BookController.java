package br.com.SpringBookstore.SpringBookstore.controller;

import br.com.SpringBookstore.SpringBookstore.domain.Book;
import br.com.SpringBookstore.SpringBookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Buscar todos os livros
    @GetMapping
    public ResponseEntity<List<Book>> findAll() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books); // Retorna 200 OK
    }

    // Buscar um livro pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Integer id) {
        Book book = bookService.findById(id); // Pode lançar BusinessLogicException (400 Bad Request)
        return ResponseEntity.ok(book);
    }

    // Criar um novo livro
    @PostMapping
    public ResponseEntity<Book> insert(@RequestBody Book book) {
        Book newBook = bookService.insert(book); // Pode lançar ConflictException (409 Conflict)
        return ResponseEntity.status(201).body(newBook); // Retorna 201 Created
    }

    // Atualizar um livro existente
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Integer id, @RequestBody Book updatedBook) {
        Book updated = bookService.update(id, updatedBook); // Pode lançar BusinessLogicException ou ConflictException
        return ResponseEntity.ok(updated);
    }

    // Deletar um livro pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        bookService.delete(id); // Pode lançar BusinessLogicException (400 Bad Request)
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}