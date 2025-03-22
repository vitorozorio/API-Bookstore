package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.DTO.ReviewDTO;
import br.com.SpringBookstore.SpringBookstore.domain.Review;
import br.com.SpringBookstore.SpringBookstore.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewDTO findById(String id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada com o ID: " + id));

        // Converter entidade para DTO
        return new ReviewDTO(
                review.getId(),
                review.getComment(),
                review.getRating(),
                review.getUser().getId(), // Apenas o ID do usuário
                review.getBook().getId()  // Apenas o ID do livro
        );
    }
}
