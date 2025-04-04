package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.Category;
import br.com.SpringBookstore.SpringBookstore.domain.DTO.CategoryDTO;
import br.com.SpringBookstore.SpringBookstore.exception.BusinessLogicException;
import br.com.SpringBookstore.SpringBookstore.exception.ConflictException;
import br.com.SpringBookstore.SpringBookstore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    protected void validateCategoryFields(CategoryDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new BusinessLogicException("O nome do Categoria é obrigatória.");
        }
        if (dto.getDescricao() == null) {
            throw new BusinessLogicException("A Descrição é obrigatória.");
        }
    }

    // Buscar todos as Categoriaes e retornar como DTO
    public List<CategoryDTO> findAll() {
        return repository.findAll().stream()
                .map(Category -> new CategoryDTO(
                        Category.getId(),
                        Category.getName(),
                        Category.getDescricao()
                ))
                .collect(Collectors.toList());
    }

    // Buscar uma Categoria pelo ID e retornar como DTO
    public CategoryDTO findById(String id) {
        Category Category = repository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Categoria não encontrada com o ID: " + id));
        return new CategoryDTO(
                Category.getId(),
                Category.getName(),
                Category.getDescricao()
        );
    }

    // Criar uma nova Categoria a partir de dados fornecidos
    public CategoryDTO insert(CategoryDTO dto) {
        // Validar se o nome do Categoria já existe
        if (repository.findByName(dto.getName()) != null) {
            throw new ConflictException("Categoria já cadastrada: " + dto.getName());
        }

        // Validação dos campos obrigatórios
        validateCategoryFields(dto);

        // Criar a entidade a partir do DTO
        Category Category = new Category(
                dto.getName(),
                dto.getDescricao()
        );

        // Salvar a Categoria no banco de dados
        Category savedCategory = repository.save(Category);

        // Retornar a Categoria como DTO
        return new CategoryDTO(
                savedCategory.getId(),
                savedCategory.getName(),
                savedCategory.getDescricao()
        );
    }

    // Atualizar uma Categoria existente a partir de dados fornecidos
    public CategoryDTO update(String id, CategoryDTO dto) {
        // Buscar o Categoria pelo ID
        Category entity = repository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Categoria não encontrada com o ID: " + id));

        // Validar se o novo nome está sendo alterado para um nome já existente
        if (!entity.getName().equalsIgnoreCase(dto.getName()) && repository.findByName(dto.getName()) != null) {
            throw new ConflictException("Categoria já cadastrada: " + dto.getName());
        }

        // Validação dos campos obrigatórios
        validateCategoryFields(dto);

        // Atualizar os campos da entidade
        entity.setName(dto.getName());
        entity.setDescricao(dto.getDescricao());
        
        // Salvar as alterações no banco de dados
        Category updatedCategory = repository.save(entity);

        // Retornar a Categoria como DTO
        return new CategoryDTO(
                updatedCategory.getId(),
                updatedCategory.getName(),
                updatedCategory.getDescricao()
        );
    }

    // Deletar uma Categoria pelo ID
    public void delete(String id) {
        repository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Categoria não encontrada com o ID: " + id));
        repository.deleteById(id);
    }
}