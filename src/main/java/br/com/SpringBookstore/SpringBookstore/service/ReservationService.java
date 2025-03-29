package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.Book;
import br.com.SpringBookstore.SpringBookstore.domain.Reservation;
import br.com.SpringBookstore.SpringBookstore.domain.User;
import br.com.SpringBookstore.SpringBookstore.domain.DTO.ReservationDTO;
import br.com.SpringBookstore.SpringBookstore.domain.enuns.ReservationStatus;
import br.com.SpringBookstore.SpringBookstore.exception.BusinessLogicException;
import br.com.SpringBookstore.SpringBookstore.repository.BookRepository;
import br.com.SpringBookstore.SpringBookstore.repository.ReservationRepository;
import br.com.SpringBookstore.SpringBookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    // Método de validação de campos obrigatórios
    private void validateReservationFields(ReservationDTO dto) {
        if (dto.getUserId() == null || dto.getUserId().trim().isEmpty()) {
            throw new BusinessLogicException("O ID do usuário é obrigatório.");
        }
        if (dto.getBookId() == null || dto.getBookId().trim().isEmpty()) {
            throw new BusinessLogicException("O ID do livro é obrigatório.");
        }
        if (dto.getStatus() == null) {
            throw new BusinessLogicException("O status da reserva é obrigatório.");
        }
    }

    // Buscar todas as reservas e retornar como DTO
    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll().stream()
                .map(reservation -> new ReservationDTO(
                        reservation.getId(),
                        reservation.getReservationDate(),
                        reservation.getStatus(),
                        reservation.getUser() != null ? reservation.getUser().getId() : null, // Pegando o ID do usuário
                        reservation.getBook() != null ? reservation.getBook().getId() : null  // Pegando o ID do livro
                ))
                .collect(Collectors.toList());
    }

    // Buscar uma reserva pelo ID e retornar como DTO
    public ReservationDTO findById(String id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Reserva não encontrada com o ID: " + id));
        return new ReservationDTO(
                reservation.getId(),
                reservation.getReservationDate(),
                reservation.getStatus(),
                reservation.getUser() != null ? reservation.getUser().getId() : null,
                reservation.getBook() != null ? reservation.getBook().getId() : null
        );
    }

    // Buscar reservas por status e retornar como DTO
    public List<ReservationDTO> findByStatus(ReservationStatus status) {
        List<Reservation> reservations = reservationRepository.findByStatus(status.name());
        return reservations.stream()
                .map(reservation -> new ReservationDTO(
                        reservation.getId(),
                        reservation.getReservationDate(),
                        reservation.getStatus(),
                        reservation.getUser() != null ? reservation.getUser().getId() : null,
                        reservation.getBook() != null ? reservation.getBook().getId() : null
                ))
                .collect(Collectors.toList());
    }

    // Buscar reservas de um usuário com paginação
    public Page<ReservationDTO> findByUserId(String userId, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findByUserId(userId, pageable);
        return reservations.map(reservation -> new ReservationDTO(
                reservation.getId(),
                reservation.getReservationDate(),
                reservation.getStatus(),
                reservation.getUser() != null ? reservation.getUser().getId() : null,
                reservation.getBook() != null ? reservation.getBook().getId() : null
        ));
    }

    // Buscar reservas de um livro com paginação
    public Page<ReservationDTO> findByBookId(String bookId, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findByBookId(bookId, pageable);
        return reservations.map(reservation -> new ReservationDTO(
                reservation.getId(),
                reservation.getReservationDate(),
                reservation.getStatus(),
                reservation.getUser() != null ? reservation.getUser().getId() : null,
                reservation.getBook() != null ? reservation.getBook().getId() : null
        ));
    }

    // Criar uma nova reserva
    public ReservationDTO createReservation(ReservationDTO dto) {
        validateReservationFields(dto);

        // Buscar usuário e livro no banco
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new BusinessLogicException("Usuário não encontrado com o ID: " + dto.getUserId()));
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new BusinessLogicException("Livro não encontrado com o ID: " + dto.getBookId()));

        // Criar a reserva
        Reservation reservation = new Reservation(
                dto.getReservationDate(),
                dto.getStatus(),
                user,
                book
        );

        // Salvar a reserva no banco
        Reservation savedReservation = reservationRepository.save(reservation);

        // Retornar a reserva como DTO
        return new ReservationDTO(
                savedReservation.getId(),
                savedReservation.getReservationDate(),
                savedReservation.getStatus(),
                savedReservation.getUser().getId(),
                savedReservation.getBook().getId()
        );
    }

    // Atualizar o status de uma reserva
    public ReservationDTO updateReservationStatus(String id, ReservationStatus status) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Reserva não encontrada com o ID: " + id));

        reservation.setStatus(status);
        reservation = reservationRepository.save(reservation);

        return new ReservationDTO(
                reservation.getId(),
                reservation.getReservationDate(),
                reservation.getStatus(),
                reservation.getUser() != null ? reservation.getUser().getId() : null,
                reservation.getBook() != null ? reservation.getBook().getId() : null
        );
    }

    // Cancelar uma reserva
    public ReservationDTO cancelReservation(String id) {
        return updateReservationStatus(id, ReservationStatus.CANCELLED);
    }

    // Confirmar uma reserva
    public ReservationDTO confirmReservation(String id) {
        return updateReservationStatus(id, ReservationStatus.CONFIRMED);
    }
}
