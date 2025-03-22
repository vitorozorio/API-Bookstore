package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.DTO.ReservationDTO;
import br.com.SpringBookstore.SpringBookstore.domain.Reservation;
import br.com.SpringBookstore.SpringBookstore.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public ReservationDTO findById(String id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada com o ID: " + id));

        // Converter entidade para DTO
        return new ReservationDTO(
                reservation.getId(),
                reservation.getReservationDate(),
                reservation.getStatus(),
                reservation.getUser().getId(), // Apenas o ID do usuário
                reservation.getBook().getId()  // Apenas o ID do livro
        );
    }
}
