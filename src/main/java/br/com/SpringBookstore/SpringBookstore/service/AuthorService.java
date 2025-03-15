package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.Author;
import br.com.SpringBookstore.SpringBookstore.exception.BusinessLogicException;
import br.com.SpringBookstore.SpringBookstore.exception.ConflictException;
import br.com.SpringBookstore.SpringBookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository repository;

    // Buscar todos os autores
    public List<Author> findAll() {
        return repository.findAll();
    }

    // Buscar um autor pelo ID
    public Author findById(Integer id) {
        Optional<Author> obj = repository.findById(id);
        if (obj.isEmpty()) {
            throw new BusinessLogicException("Autor não encontrado com o ID: " + id);
        }
        return obj.get();
    }

    // Criar um novo autor
    public Author insert(Author author) {
        // Validar se o nome do autor já existe
        if (repository.findByName(author.getname()) != null) {
            throw new ConflictException("Autor já cadastrado: " + author.getname());
        }
        return repository.save(author);
    }

    // Atualizar um autor existente
    public Author update(Integer id, Author updatedAuthor) {
        Author entity = findById(id);

        // Validar se o novo nome está sendo alterado para um nome já existente
        if (!entity.getname().equals(updatedAuthor.getname()) && repository.findByName(updatedAuthor.getname()) != null) {
            throw new ConflictException("Autor já cadastrado: " + updatedAuthor.getname());
        }

        updateData(entity, updatedAuthor);
        return repository.save(entity);
    }

    // Deletar um autor pelo ID
    public void delete(Integer id) {
        Author author = findById(id); // Verifica se existe antes de deletar
        repository.deleteById(id);
    }

    // Método auxiliar para atualizar os dados do autor
    private void updateData(Author entity, Author updatedAuthor) {
        entity.setname(updatedAuthor.getname());
        entity.setNacionalidade(updatedAuthor.getNacionalidade());
        entity.setDataNascimento(updatedAuthor.getDataNascimento());
        entity.setBiografia(updatedAuthor.getBiografia());
        entity.setBooks(updatedAuthor.getBooks());
    }
}