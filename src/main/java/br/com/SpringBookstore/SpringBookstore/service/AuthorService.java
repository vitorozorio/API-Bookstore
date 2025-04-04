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

    // Buscar todos os autores e retornar como DTO
    public List<AuthorDTO> findAll() {
        return repository.findAll().stream()
                .map(author -> new AuthorDTO(
                        author.getId(),
                        author.getName(),
                        author.getNacionalidade(),
                        author.getDataNascimento(),
                        author.getBiografia(),
                        author.getBooks().stream().map(book -> book.getId()).toList()
                ))
                .collect(Collectors.toList());
    }

    // Buscar um autor pelo ID e retornar como DTO
    public AuthorDTO findById(String id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Autor não encontrado com o ID: " + id));
        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getNacionalidade(),
                author.getDataNascimento(),
                author.getBiografia(),
                author.getBooks().stream().map(book -> book.getId()).toList()
        );
    }

    // Criar um novo autor a partir de dados fornecidos
    public AuthorDTO insert(AuthorDTO dto) {
        // Validar se o nome do autor já existe
        if (repository.findByName(dto.getName()) != null) {
            throw new ConflictException("Autor já cadastrado: " + dto.getName());
        }

        // Validar campos obrigatórios
        validaAuthorFields(dto);

        // Buscar autores no banco pelos IDs informados no DTO
        List<Book> books = dto.getBookIds().stream()
                .map(id -> bookRepository.findById(id)
                        .orElseThrow(() -> new BusinessLogicException("Autor não encontrado: " + id)))
                .collect(Collectors.toList());

        // Criar a entidade a partir do DTO
        Author author = new Author(
                dto.getName(),
                dto.getNacionalidade(),
                dto.getDataNascimento(),
                dto.getBiografia(),
                books
        );

        // Salvar o autor no banco de dados
        Author savedAuthor = repository.save(author);

        // Retornar o autor como DTO
        return new AuthorDTO(
                savedAuthor.getId(),
                savedAuthor.getName(),
                savedAuthor.getNacionalidade(),
                savedAuthor.getDataNascimento(),
                savedAuthor.getBiografia(),
                savedAuthor.getBooks().stream().map(book -> book.getId()).toList()
        );
    }

    // Atualizar um autor existente a partir de dados fornecidos
    public AuthorDTO update(String id, AuthorDTO dto) {
        // Buscar o autor pelo ID
        Author entity = repository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Autor não encontrado com o ID: " + id));

        // Validar se o novo nome está sendo alterado para um nome já existente
        if (!entity.getName().equalsIgnoreCase(dto.getName()) && repository.findByName(dto.getName()) != null) {
            throw new ConflictException("Autor já cadastrado: " + dto.getName());
        }

        // Validar campos obrigatórios
        validaAuthorFields(dto);

        // Atualizar os campos da entidade
        entity.setName(dto.getName());
        entity.setNacionalidade(dto.getNacionalidade());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setBiografia(dto.getBiografia());

        // Salvar as alterações no banco de dados
        Author updatedAuthor = repository.save(entity);

        // Retornar o autor como DTO
        return new AuthorDTO(
                updatedAuthor.getId(),
                updatedAuthor.getName(),
                updatedAuthor.getNacionalidade(),
                updatedAuthor.getDataNascimento(),
                updatedAuthor.getBiografia(),
                updatedAuthor.getBooks().stream().map(book -> book.getId()).toList()
        );
    }

    // Deletar um autor pelo ID
    public void delete(String id) {
        repository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Autor não encontrado com o ID: " + id));
        repository.deleteById(id);
    }
}