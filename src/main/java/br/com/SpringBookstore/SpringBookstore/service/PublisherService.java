package br.com.SpringBookstore.SpringBookstore.service;


import br.com.SpringBookstore.SpringBookstore.domain.Publisher;
import br.com.SpringBookstore.SpringBookstore.exception.BusinessLogicException;
import br.com.SpringBookstore.SpringBookstore.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;


    public List<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    public Publisher findById(String id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Editora não encontrada com o ID: " + id));
    }

    public Publisher insert(Publisher publisher) {
         return publisherRepository.save(publisher);
    }

    public Publisher update(String id, Publisher publisher) {
         publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Editora não encontrada com o ID: " + id));

        publisher.setName(publisher.getName());
        publisher.setEndereco(publisher.getEndereco());

        return publisherRepository.save(publisher);
    }

    public void delete(String id) {
        publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Editora não encontrada com o ID: " + id));
        publisherRepository.deleteById(id);
    }
}
