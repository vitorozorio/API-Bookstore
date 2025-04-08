package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.Author;
import br.com.SpringBookstore.SpringBookstore.domain.Book;
import br.com.SpringBookstore.SpringBookstore.domain.Category;
import br.com.SpringBookstore.SpringBookstore.domain.Review;
import br.com.SpringBookstore.SpringBookstore.domain.DTO.BookDTO;
import br.com.SpringBookstore.SpringBookstore.exception.BusinessLogicException;
import br.com.SpringBookstore.SpringBookstore.exception.ConflictException;
import br.com.SpringBookstore.SpringBookstore.repository.AuthorRepository;
import br.com.SpringBookstore.SpringBookstore.repository.BookRepository;
import br.com.SpringBookstore.SpringBookstore.repository.CategoryRepository;
import br.com.SpringBookstore.SpringBookstore.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    // Valida os campos obrigatórios do livro
    protected void validateBookFields(BookDTO dto) {
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new BusinessLogicException("O título do livro é obrigatório.");
        }
        if (dto.getIsbn() == null || dto.getIsbn().trim().isEmpty()) {
            throw new BusinessLogicException("O ISBN do livro é obrigatório.");
        }
        if (dto.getPublicationYear() == null) {
            throw new BusinessLogicException("O ano de publicação do livro é obrigatório.");
        }
        if (dto.getPublisher() == null || dto.getPublisher().trim().isEmpty()) {
            throw new BusinessLogicException("A editora do livro é obrigatória.");
        }
        if (dto.getAvailableQuantity() == null || dto.getAvailableQuantity() < 0) {
            throw new BusinessLogicException("A quantidade disponível deve ser maior ou igual a zero.");
        }
        if (dto.getStatus() == null) {
            throw new BusinessLogicException("O status do livro é obrigatório.");
        }
        if (dto.getCategoryId() == null) {
            throw new BusinessLogicException("A categoria do livro é obrigatória.");
        }
    }

    // Converte entidade Book para DTO
    protected BookDTO toDTO(Book book){
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getPublicationYear(),
                book.getPublisher(),
                book.getAvailableQuantity(),
                book.getStatus(),
                book.getCategory() != null ? book.getCategory().getId() : null,
                book.getAuthors() != null ? book.getAuthors().stream().map(Author::getId).collect(Collectors.toList()) : null,
                book.getReviews() != null ? book.getReviews().stream().map(Review::getId).collect(Collectors.toList()) : null
        );
    }

    // Verifica se já existe outro livro com o mesmo título
    private void checkIfTitleAlreadyExists(String title, String idToIgnore) {
        Optional<Book> existing = bookRepository.findByTitle(title);
        if (existing.isPresent() && (idToIgnore == null || !existing.get().getId().equals(idToIgnore))) {
            throw new ConflictException("Já existe um livro com este título: " + title);
        }
    }

    // Retorna todos os livros
    public List<BookDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Busca livro pelo ID
    public BookDTO findById(String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Livro não encontrado com o ID: " + id));
        return toDTO(book);
    }

    // Busca livro pelo título
    public BookDTO findByTitle(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new BusinessLogicException("Livro não encontrado com o título: " + title));
        return toDTO(book);
    }

    // Busca livro pelo ISBN
    public BookDTO findByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new BusinessLogicException("Livro não encontrado com o ISBN: " + isbn);
        }
        return toDTO(book);
    }

    // Insere um novo livro
    public BookDTO insert(BookDTO dto) {
        checkIfTitleAlreadyExists(dto.getTitle(), null);
        validateBookFields(dto);

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new BusinessLogicException("Categoria não encontrada."));

        List<Author> authors = dto.getAuthorIds() != null ?
                dto.getAuthorIds().stream()
                        .map(id -> authorRepository.findById(id)
                                .orElseThrow(() -> new BusinessLogicException("Autor não encontrado: " + id)))
                        .collect(Collectors.toList())
                : List.of();

        List<Review> reviews = dto.getReviewIds() != null ?
                dto.getReviewIds().stream()
                        .map(id -> reviewRepository.findById(id)
                                .orElseThrow(() -> new BusinessLogicException("Avaliação não encontrada: " + id)))
                        .collect(Collectors.toList())
                : List.of();

        Book book = new Book(
                dto.getTitle(),
                dto.getIsbn(),
                dto.getPublicationYear(),
                dto.getPublisher(),
                dto.getAvailableQuantity(),
                dto.getStatus(),
                category,
                authors,
                reviews
        );

        // Salva o livro primeiro
        Book savedBook = bookRepository.save(book);

        // Atualiza os autores para manter a relação bidirecional
        for (Author author : authors) {
            author.getBooks().add(savedBook);
            authorRepository.save(author);
        }

        return toDTO(savedBook);
    }

    // Atualiza um livro existente
    public BookDTO update(String id, BookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Livro não encontrado com o ID: " + id));

        checkIfTitleAlreadyExists(dto.getTitle(), id);
        validateBookFields(dto);

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new BusinessLogicException("Categoria não encontrada."));

        List<Author> authors = dto.getAuthorIds().stream()
                .map(authorId -> authorRepository.findById(authorId)
                        .orElseThrow(() -> new BusinessLogicException("Autor não encontrado: " + authorId)))
                .collect(Collectors.toList());

        List<Review> reviews = dto.getReviewIds().stream()
                .map(reviewId -> reviewRepository.findById(reviewId)
                        .orElseThrow(() -> new BusinessLogicException("Avaliação não encontrada: " + reviewId)))
                .collect(Collectors.toList());

        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPublicationYear(dto.getPublicationYear());
        book.setPublisher(dto.getPublisher());
        book.setAvailableQuantity(dto.getAvailableQuantity());
        book.setStatus(dto.getStatus());
        book.setCategory(category);
        book.setAuthors(authors);
        book.setReviews(reviews);

        Book updatedBook = bookRepository.save(book);
        return toDTO(updatedBook);
    }

    // Deleta um livro pelo ID
    public void delete(String id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Livro não encontrado com o ID: " + id));
        bookRepository.deleteById(id);
    }
}
