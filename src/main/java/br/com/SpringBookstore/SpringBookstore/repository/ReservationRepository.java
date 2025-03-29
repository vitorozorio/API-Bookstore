package br.com.SpringBookstore.SpringBookstore.repository;

import br.com.SpringBookstore.SpringBookstore.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

    // Encontrar reservas de um usuário
    List<Reservation> findByUserId(String userId);

    // Encontrar reservas de um livro
    List<Reservation> findByBookId(String bookId);

    // Encontrar reservas por status
    List<Reservation> findByStatus(String status);

    // Encontrar reservas de um usuário com paginação
    Page<Reservation> findByUserId(String userId, Pageable pageable);

    // Encontrar reservas de um livro com paginação
    Page<Reservation> findByBookId(String bookId, Pageable pageable);

    // Encontrar reservas com status específico com paginação e ordenação
    Page<Reservation> findByStatus(String status, Pageable pageable);
}
