package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.Reservation;
import br.com.SpringBookstore.SpringBookstore.domain.enuns.ReservationStatus;
import br.com.SpringBookstore.SpringBookstore.exception.BusinessLogicException;
import br.com.SpringBookstore.SpringBookstore.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;


    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation findById(String id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Reserva não encontrada com o ID: " + id));
    }

    public List<Reservation> findByStatus(ReservationStatus status) {
        return reservationRepository.findByStatus(status.name());
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

}
