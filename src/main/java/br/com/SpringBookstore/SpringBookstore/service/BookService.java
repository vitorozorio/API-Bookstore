package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.Book;
import br.com.SpringBookstore.SpringBookstore.exception.BusinessLogicException;
import br.com.SpringBookstore.SpringBookstore.exception.ConflictException;
import br.com.SpringBookstore.SpringBookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    // Buscar todos os livros
    public List<Book> findAll() {
        return repository.findAll();
    }

    // Buscar um livro pelo ID
    public Book findById(Integer id) {
        Optional<Book> obj = repository.findById(id);
        if (obj.isEmpty()) {
            throw new BusinessLogicException("Livro não encontrado com o ID: " + id);
        }
        return obj.get();
    }

    // Criar um novo livro
    public Book insert(Book book) {
        // Validar se o título já existe
        if (repository.findByTitle(book.getTitle()) != null) {
            throw new ConflictException("Título já cadastrado: " + book.getTitle());
        }

        // Validar se o ISBN já existe
        if (repository.findByIsbn(book.getIsbn()) != null) {
            throw new ConflictException("ISBN já cadastrado: " + book.getIsbn());
        }

        return repository.save(book);
    }

    // Deletar um livro pelo ID
    public void delete(Integer id) {
        // Validar se o livro existe
        if (!repository.existsById(id)) {
            throw new BusinessLogicException("Livro não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    // Atualizar um livro existente
    public Book update(Integer id, Book updatedBook) {
        Book entity = findById(id); // Verifica se o livro existe

        // Validar se o título está sendo alterado para um título já existente
        if (!entity.getTitle().equals(updatedBook.getTitle()) && repository.findByTitle(updatedBook.getTitle()) != null) {
            throw new ConflictException("Título já cadastrado: " + updatedBook.getTitle());
        }

        // Validar se o ISBN está sendo alterado para um ISBN já existente
        if (!entity.getIsbn().equals(updatedBook.getIsbn()) && repository.findByIsbn(updatedBook.getIsbn()) != null) {
            throw new ConflictException("ISBN já cadastrado: " + updatedBook.getIsbn());
        }

        updateData(entity, updatedBook);
        return repository.save(entity);
    }

    // Método auxiliar para atualizar os dados do livro
    private void updateData(Book entity, Book updatedBook) {
        entity.setTitle(updatedBook.getTitle());
        entity.setIsbn(updatedBook.getIsbn());
        entity.setPublicationYear(updatedBook.getPublicationYear());
        entity.setPublisher(updatedBook.getPublisher());
        entity.setAvailableQuantity(updatedBook.getAvailableQuantity());
        entity.setStatus(updatedBook.getStatus());
        entity.setCategory(updatedBook.getCategory());
        entity.setAuthors(updatedBook.getAuthors());
        entity.setReviews(updatedBook.getReviews());
    }
}