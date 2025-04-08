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

    private ReservationDTO toDTO(Reservation reservation) {
        return new ReservationDTO(
                reservation.getId(),
                reservation.getReservationDate(),
                reservation.getStatus(),
                reservation.getUser() != null ? reservation.getUser().getId() : null,
                reservation.getBook() != null ? reservation.getBook().getId() : null
        );
    }

    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ReservationDTO findById(String id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Reserva não encontrada com o ID: " + id));
        return toDTO(reservation);
    }

    public List<ReservationDTO> findByStatus(ReservationStatus status) {
        return reservationRepository.findByStatus(status.name())
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Page<ReservationDTO> findByUserId(String userId, Pageable pageable) {
        return reservationRepository.findByUserId(userId, pageable)
                .map(this::toDTO);
    }

    public Page<ReservationDTO> findByBookId(String bookId, Pageable pageable) {
        return reservationRepository.findByBookId(bookId, pageable)
                .map(this::toDTO);
    }

    public ReservationDTO createReservation(ReservationDTO dto) {
        validateReservationFields(dto);

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new BusinessLogicException("Usuário não encontrado com o ID: " + dto.getUserId()));
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new BusinessLogicException("Livro não encontrado com o ID: " + dto.getBookId()));

        Reservation reservation = new Reservation(
                dto.getReservationDate(),
                dto.getStatus(),
                user,
                book
        );

        return toDTO(reservationRepository.save(reservation));
    }

    public ReservationDTO updateReservationStatus(String id, ReservationStatus status) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Reserva não encontrada com o ID: " + id));

        reservation.setStatus(status);
        return toDTO(reservationRepository.save(reservation));
    }

    public ReservationDTO cancelReservation(String id) {
        return updateReservationStatus(id, ReservationStatus.CANCELLED);
    }

    public ReservationDTO confirmReservation(String id) {
        return updateReservationStatus(id, ReservationStatus.CONFIRMED);
    }
}
