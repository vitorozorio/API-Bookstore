package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.DTO.PublisherDTO;
import br.com.SpringBookstore.SpringBookstore.domain.Publisher;
import br.com.SpringBookstore.SpringBookstore.exception.BusinessLogicException;
import br.com.SpringBookstore.SpringBookstore.exception.ConflictException;
import br.com.SpringBookstore.SpringBookstore.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    // Valida os campos obrigatórios
    protected void validatePublisherFields(PublisherDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new BusinessLogicException("O nome da editora é obrigatório.");
        }
        if (dto.getEndereco() == null) {
            throw new BusinessLogicException("O endereço da editora é obrigatório.");
        }
    }

    // Lista todas as editoras
    public List<PublisherDTO> findAll() {
        return publisherRepository.findAll().stream()
                .map(publisher -> new PublisherDTO(
                        publisher.getId(),
                        publisher.getName(),
                        publisher.getEndereco()
                ))
                .collect(Collectors.toList());
    }

    // Busca uma editora pelo ID
    public PublisherDTO findById(String id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Editora não encontrada com o ID: " + id));
        return new PublisherDTO(
                publisher.getId(),
                publisher.getName(),
                publisher.getEndereco()
        );
    }

    // Cria uma nova editora
    public PublisherDTO insert(PublisherDTO dto) {
        if (publisherRepository.findByName(dto.getName()) != null) {
            throw new ConflictException("Editora já cadastrada: " + dto.getName());
        }

        validatePublisherFields(dto);

        Publisher publisher = new Publisher(
                dto.getName(),
                dto.getEndereco()
        );

        Publisher savedPublisher = publisherRepository.save(publisher);

        return new PublisherDTO(
                savedPublisher.getId(),
                savedPublisher.getName(),
                savedPublisher.getEndereco()
        );
    }

    // Atualiza uma editora existente
    public PublisherDTO update(String id, PublisherDTO dto) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Editora não encontrada com o ID: " + id));

        if (!publisher.getName().equalsIgnoreCase(dto.getName())
                && publisherRepository.findByName(dto.getName()) != null) {
            throw new ConflictException("Editora já cadastrada: " + dto.getName());
        }

        validatePublisherFields(dto);

        publisher.setName(dto.getName());
        publisher.setEndereco(dto.getEndereco());

        Publisher updatedPublisher = publisherRepository.save(publisher);

        return new PublisherDTO(
                updatedPublisher.getId(),
                updatedPublisher.getName(),
                updatedPublisher.getEndereco()
        );
    }

    // Deleta uma editora (futuramente, mudar para inativação)
    public void delete(String id) {
        publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Editora não encontrada com o ID: " + id));
        publisherRepository.deleteById(id);
    }
}
