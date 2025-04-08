package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.Author;
import br.com.SpringBookstore.SpringBookstore.domain.Book;
import br.com.SpringBookstore.SpringBookstore.domain.DTO.AuthorDTO;
import br.com.SpringBookstore.SpringBookstore.exception.BusinessLogicException;
import br.com.SpringBookstore.SpringBookstore.exception.ConflictException;
import br.com.SpringBookstore.SpringBookstore.repository.AuthorRepository;
import br.com.SpringBookstore.SpringBookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private BookRepository bookRepository;

    // Validação de campos obrigatórios
    protected void validateAuthorFields(AuthorDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new BusinessLogicException("O nome do autor é obrigatório.");
        }
        if (dto.getNacionalidade() == null) {
            throw new BusinessLogicException("A nacionalidade do autor é obrigatória.");
        }
        if (dto.getDataNascimento() == null) {
            throw new BusinessLogicException("A data de nascimento do autor é obrigatória.");
        }
        if (dto.getBiografia() == null) {
            throw new BusinessLogicException("A biografia do autor é obrigatória.");
        }
    }

    // Conversão de entidade para DTO
    private AuthorDTO toDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getNacionalidade(),
                author.getDataNascimento(),
                author.getBiografia(),
                author.getBooks().stream()
                        .map(Book::getId)
                        .collect(Collectors.toList())
        );
    }

    // Conversão de DTO para entidade
    private Author toEntity(AuthorDTO dto) {
        List<Book> books = dto.getBookIds().stream()
                .map(id -> bookRepository.findById(id)
                        .orElseThrow(() -> new BusinessLogicException("Livro não encontrado com o ID: " + id)))
                .collect(Collectors.toList());

        return new Author(
                dto.getName().trim(),
                dto.getNacionalidade(),
                dto.getDataNascimento(),
                dto.getBiografia(),
                books
        );
    }

    // Buscar todos os autores
    public List<AuthorDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar autor por ID
    public AuthorDTO findById(String id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Autor não encontrado com o ID: " + id));
        return toDTO(author);
    }

    // Inserir novo autor
    public AuthorDTO insert(AuthorDTO dto) {
        validateAuthorFields(dto);

        String nameTrimmed = dto.getName().trim();
        Author existingAuthor = repository.findByName(nameTrimmed);
        if (existingAuthor != null) {
            throw new ConflictException("Autor já cadastrado: " + nameTrimmed);
        }

        Author author = toEntity(dto);
        Author savedAuthor = repository.save(author);

        // Atualiza os livros para manter a relação bidirecional
        for (Book book : author.getBooks()) {
            book.getAuthors().add(savedAuthor);
            bookRepository.save(book);
        }

        return toDTO(savedAuthor);
    }


    // Atualizar autor existente
    public AuthorDTO update(String id, AuthorDTO dto) {
        validateAuthorFields(dto);

        Author entity = repository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Autor não encontrado com o ID: " + id));

        String newNameTrimmed = dto.getName().trim();
        if (!entity.getName().equalsIgnoreCase(newNameTrimmed)
                && repository.findByName(newNameTrimmed) != null) {
            throw new ConflictException("Autor já cadastrado: " + newNameTrimmed);
        }

        entity.setName(newNameTrimmed);
        entity.setNacionalidade(dto.getNacionalidade());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setBiografia(dto.getBiografia());

        Author updatedAuthor = repository.save(entity);
        return toDTO(updatedAuthor);
    }

    // Deletar autor por ID (com verificação de livros)
    public void delete(String id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Autor não encontrado com o ID: " + id));

        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
            throw new BusinessLogicException("Não é possível excluir um autor com livros associados.");
        }

        repository.deleteById(id);
    }
}
