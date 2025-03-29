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

    // Método de validação de campos obrigatórios
    private void validateBookFields(BookDTO dto) {
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
    }

    // Buscar todos os livros e retornar como DTO
    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(book -> new BookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getIsbn(),
                        book.getPublicationYear(),
                        book.getPublisher(),
                        book.getAvailableQuantity(),
                        book.getStatus(),
                        book.getCategory() != null ? book.getCategory().getId() : null, // Pegando o ID da categoria
                        book.getAuthors() != null ? book.getAuthors().stream().map(author -> author.getId()).collect(Collectors.toList()) : null, // Pegando IDs dos autores
                        book.getReviews() != null ? book.getReviews().stream().map(review -> review.getId()).collect(Collectors.toList()) : null // Pegando IDs das avaliações
                ))
                .collect(Collectors.toList());
    }

    // Buscar um livro pelo ID e retornar como DTO
    public BookDTO findById(String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Livro não encontrado com o ID: " + id));
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getPublicationYear(),
                book.getPublisher(),
                book.getAvailableQuantity(),
                book.getStatus(),
                book.getCategory() != null ? book.getCategory().getId() : null, // Pegando o ID da categoria
                book.getAuthors() != null ? book.getAuthors().stream().map(author -> author.getId()).collect(Collectors.toList()) : null, // Pegando IDs dos autores
                book.getReviews() != null ? book.getReviews().stream().map(review -> review.getId()).collect(Collectors.toList()) : null // Pegando IDs das avaliações
        );
    }

    // Buscar um livro pelo título e retornar como DTO
    public BookDTO findByTitle(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new BusinessLogicException("Livro não encontrado com o título: " + title));
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

    // Buscar um livro pelo ISBN e retornar como DTO
    public BookDTO findByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new BusinessLogicException("Livro não encontrado com o ISBN: " + isbn);
        }
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

    // Inserir um novo livro
    public BookDTO insert(BookDTO dto) {
        // Verifica se o livro já existe pelo título
        Optional<Book> existingBook = bookRepository.findByTitle(dto.getTitle());

        if (existingBook.isPresent()) {
            throw new ConflictException("Livro já cadastrado: " + dto.getTitle());
        }

        // Validação dos campos obrigatórios
        validateBookFields(dto);

        // Buscar categoria no banco pelo ID informado no DTO
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new BusinessLogicException("Categoria não encontrada."));

        // Buscar autores no banco pelos IDs informados no DTO
        List<Author> authors = dto.getAuthorIds().stream()
                .map(id -> authorRepository.findById(id)
                        .orElseThrow(() -> new BusinessLogicException("Autor não encontrado: " + id)))
                .collect(Collectors.toList());

        // Buscar avaliações no banco pelos IDs informados no DTO
        List<Review> reviews = dto.getReviewIds().stream()
                .map(id -> reviewRepository.findById(id)
                        .orElseThrow(() -> new BusinessLogicException("Avaliação não encontrada: " + id)))
                .collect(Collectors.toList());

        // Criar a entidade Book
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

        // Salvar no banco
        Book savedBook = bookRepository.save(book);

        // Retornar o DTO do livro salvo
        return new BookDTO(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getIsbn(),
                savedBook.getPublicationYear(),
                savedBook.getPublisher(),
                savedBook.getAvailableQuantity(),
                savedBook.getStatus(),
                savedBook.getCategory().getId(),
                savedBook.getAuthors().stream().map(Author::getId).collect(Collectors.toList()),
                savedBook.getReviews().stream().map(Review::getId).collect(Collectors.toList())
        );
    }

    // Atualizar um livro existente
    public BookDTO update(String id, BookDTO dto) {
        // Buscar o livro pelo ID
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Livro não encontrado com o ID: " + id));

        // Verifica se outro livro com o mesmo título já existe
        Optional<Book> existingBook = bookRepository.findByTitle(dto.getTitle());

        if (existingBook.isPresent() && !existingBook.get().getId().equals(id)) {
            throw new ConflictException("Já existe um livro com este título: " + dto.getTitle());
        }

        // Validação dos campos obrigatórios
        validateBookFields(dto);

        // Buscar categoria no banco pelo ID informado no DTO
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new BusinessLogicException("Categoria não encontrada."));

        // Buscar autores no banco pelos IDs informados no DTO
        List<Author> authors = dto.getAuthorIds().stream()
                .map(idAuthor -> authorRepository.findById(idAuthor)
                        .orElseThrow(() -> new BusinessLogicException("Autor não encontrado: " + idAuthor)))
                .collect(Collectors.toList());

        // Buscar avaliações no banco pelos IDs informados no DTO
        List<Review> reviews = dto.getReviewIds().stream()
                .map(idReview -> reviewRepository.findById(idReview)
                        .orElseThrow(() -> new BusinessLogicException("Avaliação não encontrada: " + idReview)))
                .collect(Collectors.toList());

        // Atualizar os campos da entidade
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPublicationYear(dto.getPublicationYear());
        book.setPublisher(dto.getPublisher());
        book.setAvailableQuantity(dto.getAvailableQuantity());
        book.setStatus(dto.getStatus());
        book.setCategory(category);
        book.setAuthors(authors);
        book.setReviews(reviews);

        // Salvar as alterações no banco de dados
        Book updatedBook = bookRepository.save(book);

        // Retornar o livro como DTO
        return new BookDTO(
                updatedBook.getId(),
                updatedBook.getTitle(),
                updatedBook.getIsbn(),
                updatedBook.getPublicationYear(),
                updatedBook.getPublisher(),
                updatedBook.getAvailableQuantity(),
                updatedBook.getStatus(),
                updatedBook.getCategory().getId(),
                updatedBook.getAuthors().stream().map(Author::getId).collect(Collectors.toList()),
                updatedBook.getReviews().stream().map(Review::getId).collect(Collectors.toList())
        );
    }

    // Deletar um livro pelo ID
    public void delete(String id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Livro não encontrado com o ID: " + id));
        bookRepository.deleteById(id);
    }
}
