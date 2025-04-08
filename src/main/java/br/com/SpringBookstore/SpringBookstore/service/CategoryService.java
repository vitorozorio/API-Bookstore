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

    // Valida os campos obrigatórios de uma categoria
    protected void validateCategoryFields(CategoryDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new BusinessLogicException("O nome da categoria é obrigatório.");
        }
        if (dto.getDescricao() == null || dto.getDescricao().trim().isEmpty()) {
            throw new BusinessLogicException("A descrição da categoria é obrigatória.");
        }
    }

    // Converte uma entidade Category para CategoryDTO
    protected CategoryDTO toDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescricao()
        );
    }

    // Retorna todas as categorias cadastradas
    public List<CategoryDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Busca uma categoria pelo ID
    public CategoryDTO findById(String id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Categoria não encontrada com o ID: " + id));
        return toDTO(category);
    }

    // Insere uma nova categoria
    public CategoryDTO insert(CategoryDTO dto) {
        if (repository.findByName(dto.getName()) != null) {
            throw new ConflictException("Categoria já cadastrada: " + dto.getName());
        }

        validateCategoryFields(dto);

        Category category = new Category(
                dto.getName(),
                dto.getDescricao()
        );

        Category savedCategory = repository.save(category);

        return toDTO(savedCategory);
    }

    // Atualiza uma categoria existente
    public CategoryDTO update(String id, CategoryDTO dto) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Categoria não encontrada com o ID: " + id));

        if (!category.getName().equalsIgnoreCase(dto.getName()) &&
                repository.findByName(dto.getName()) != null) {
            throw new ConflictException("Categoria já cadastrada: " + dto.getName());
        }

        validateCategoryFields(dto);

        category.setName(dto.getName());
        category.setDescricao(dto.getDescricao());

        Category updatedCategory = repository.save(category);

        return toDTO(updatedCategory);
    }

    // Deleta uma categoria pelo ID
    public void delete(String id) {
        repository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Categoria não encontrada com o ID: " + id));
        repository.deleteById(id);
    }
}
