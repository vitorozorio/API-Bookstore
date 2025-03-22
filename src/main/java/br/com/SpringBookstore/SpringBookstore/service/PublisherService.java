package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.DTO.PublisherDTO;
import br.com.SpringBookstore.SpringBookstore.domain.Publisher;
import br.com.SpringBookstore.SpringBookstore.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public PublisherDTO findById(String id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Editora não encontrada com o ID: " + id));

        // Converter entidade para DTO
        return new PublisherDTO(
                publisher.getId(),
                publisher.getName(),
                publisher.getEndereco()
        );
    }
}
