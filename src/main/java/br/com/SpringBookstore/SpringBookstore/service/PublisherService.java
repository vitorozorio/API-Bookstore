package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.DTO.BookDTO;
import br.com.SpringBookstore.SpringBookstore.domain.Publisher;
import br.com.SpringBookstore.SpringBookstore.domain.DTO.PublisherDTO;
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

    protected void validatePublisherFields(PublisherDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new BusinessLogicException("O nome do editora é obrigatório.");
        }
        if (dto.getEndereco() == null) {
            throw new BusinessLogicException("O endereço da editora é obrigatório.");
        }
    }

    // Buscar todoas as editora e retornar como DTO
    public List<PublisherDTO> findAll() {
        return publisherRepository.findAll().stream()
                .map(Publisher -> new PublisherDTO(
                        Publisher.getId(),
                        Publisher.getName(),
                        Publisher.getEndereco()
                ))
                .collect(Collectors.toList());
    }

    // Buscar uma editora pelo ID e retornar como DTO
    public PublisherDTO findById(String id) {
        Publisher Publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Editora não encontrada com o ID: " + id));
        return new PublisherDTO(
                Publisher.getId(),
                Publisher.getName(),
                Publisher.getEndereco()
        );
    }

    // Criar uma nova editora a partir de dados fornecidos
    public PublisherDTO insert(PublisherDTO dto) {
        // Validar se o nome do Categoria já existe
        if (publisherRepository.findByName(dto.getName()) != null) {
            throw new ConflictException("Editora já cadastrada: " + dto.getName());
        }

        // Validação dos campos obrigatórios
        validatePublisherFields(dto);

        // Criar a entidade a partir do DTO
        Publisher Publisher = new Publisher(
                dto.getName(),
                dto.getEndereco()
        );

        // Salvar a editora no banco de dados
        Publisher savedPublisher = publisherRepository.save(Publisher);

        // Retornar a editora como DTO
        return new PublisherDTO(
                savedPublisher.getId(),
                savedPublisher.getName(),
                savedPublisher.getEndereco()
        );
    }

    // Atualizar uma editora existente a partir de dados fornecidos
    public PublisherDTO update(String id, PublisherDTO dto) {
        // Buscar o Categoria pelo ID
        Publisher entity = publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Editora não encontrada com o ID: " + id));

        // Validar se o novo nome está sendo alterado para um nome já existente
        if (!entity.getName().equalsIgnoreCase(dto.getName()) && publisherRepository.findByName(dto.getName()) != null) {
            throw new ConflictException("Editora já cadastrada: " + dto.getName());
        }

        // Validação dos campos obrigatórios
        validatePublisherFields(dto);

        // Atualizar os campos da entidade
        entity.setName(dto.getName());
        entity.setEndereco(dto.getEndereco());

        // Salvar as alterações no banco de dados
        Publisher updatedPublisher = publisherRepository.save(entity);

        // Retornar a editora como DTO
        return new PublisherDTO(
                updatedPublisher.getId(),
                updatedPublisher.getName(),
                updatedPublisher.getEndereco()
        );
    }

    // Deletar uma editora pelo ID -> refatorar em breve para inativar ao envés de deletar
    public void delete(String id) {
        publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Editora não encontrada com o ID: " + id));
        publisherRepository.deleteById(id);
    }
}