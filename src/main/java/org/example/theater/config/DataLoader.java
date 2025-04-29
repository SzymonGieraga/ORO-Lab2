package org.example.theater.config;

import jakarta.annotation.PostConstruct;
import org.example.theater.model.ReservationType;
import org.example.theater.model.TicketType;
import org.example.theater.repo.ReservationTypeRepo;
import org.example.theater.repo.TicketTypeRepo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataLoader {

    private final ReservationTypeRepo reservationTypeRepo;

    private final TicketTypeRepo ticketTypeRepo;

    public DataLoader(ReservationTypeRepo reservationTypeRepo, TicketTypeRepo ticketTypeRepo) {
        this.reservationTypeRepo = reservationTypeRepo;
        this.ticketTypeRepo = ticketTypeRepo;
    }

    @PostConstruct
    @Transactional
    public void loadData() {
        if (reservationTypeRepo.count() == 0) {
            reservationTypeRepo.save(new ReservationType(1L, ReservationType.PENDING));
            reservationTypeRepo.save(new ReservationType(2L, ReservationType.ACCEPTED));
            reservationTypeRepo.save(new ReservationType(3L, ReservationType.EXPIRED));
        }

        if (ticketTypeRepo.count() == 0) {
            ticketTypeRepo.save(new TicketType(1L, TicketType.NORMAL, 1.0));
            ticketTypeRepo.save(new TicketType(2L, TicketType.REDUCED, 0.9));
            ticketTypeRepo.save(new TicketType(3L, TicketType.STUDENT, 0.7));
        }
    }
}