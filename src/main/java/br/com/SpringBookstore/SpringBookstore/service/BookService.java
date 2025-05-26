package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.Book;
import br.com.SpringBookstore.SpringBookstore.exception.BusinessLogicException;
import br.com.SpringBookstore.SpringBookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Livro não encontrado com o ID: " + id));
    }

    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title)
                .orElseThrow(() -> new BusinessLogicException("Livro não encontrado com o título: " + title));
    }

    public Book insert(Book book) {
        return bookRepository.save(book);
    }

    public Book update(String id, Book book) {
        bookRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Livro não encontrado com o ID: " + id));

        book.setTitle(book.getTitle());
        book.setIsbn(book.getIsbn());
        book.setPublicationYear(book.getPublicationYear());
        book.setPublisher(book.getPublisher());
        book.setAvailableQuantity(book.getAvailableQuantity());
        book.setStatus(book.getStatus());

        return  bookRepository.save(book);
    }

    public void delete(String id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Livro não encontrado com o ID: " + id));
        bookRepository.deleteById(id);
    }
}
