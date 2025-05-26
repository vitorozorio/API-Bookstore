package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.Author;
import br.com.SpringBookstore.SpringBookstore.exception.BusinessLogicException;
import br.com.SpringBookstore.SpringBookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository autorRepository;


    public List<Author> findAll() {
        return autorRepository.findAll();
    }

    public Author findById(String id) {
        return  autorRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Autor não encontrado com o ID: " + id));
    }

    public Author insert(Author author) {
        return  autorRepository.save(author);
    }

    public Author update(String id, Author author) {
        Author entity = autorRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Autor não encontrado com o ID: " + id));

        entity.setName(author.getName());
        entity.setNacionalidade(author.getNacionalidade());
        entity.setDataNascimento(author.getDataNascimento());
        entity.setBiografia(author.getBiografia());

        return  autorRepository.save(entity);
    }

    public void delete(String id) {
        Author author = autorRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Autor não encontrado com o ID: " + id));

        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
            throw new BusinessLogicException("Não é possível excluir um autor com livros associados.");
        }

        autorRepository.deleteById(id);
    }
}
