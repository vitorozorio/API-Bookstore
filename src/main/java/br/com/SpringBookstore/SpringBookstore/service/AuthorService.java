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
    protected void validaAuthorFields(AuthorDTO dto) {
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
                dto.getName(),
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
        if (repository.findByName(dto.getName()) != null) {
            throw new ConflictException("Autor já cadastrado: " + dto.getName());
        }

        validaAuthorFields(dto);

        Author author = toEntity(dto);
        Author savedAuthor = repository.save(author);

        return toDTO(savedAuthor);
    }

    // Atualizar autor existente
    public AuthorDTO update(String id, AuthorDTO dto) {
        Author entity = repository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Autor não encontrado com o ID: " + id));

        if (!entity.getName().equalsIgnoreCase(dto.getName())
                && repository.findByName(dto.getName()) != null) {
            throw new ConflictException("Autor já cadastrado: " + dto.getName());
        }

        validaAuthorFields(dto);

        // Atualiza os campos (menos livros, assumindo que não muda aqui)
        entity.setName(dto.getName());
        entity.setNacionalidade(dto.getNacionalidade());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setBiografia(dto.getBiografia());

        Author updatedAuthor = repository.save(entity);

        return toDTO(updatedAuthor);
    }

    // Deletar autor por ID
    public void delete(String id) {
        repository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Autor não encontrado com o ID: " + id));
        repository.deleteById(id);
    }
}
