package org.example.theater.repo;

import org.example.theater.model.Show;
import org.example.theater.model.Client;
import org.example.theater.model.ReservationType;
import org.example.theater.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TicketRepo extends JpaRepository<Ticket, Long> {
    @Query("SELECT t.client FROM Ticket t WHERE t.show.id = :showId")
    Page<Client> findClientsByShowId(@Param("showId") Long playId, Pageable pageable);

    @Query("SELECT t.show FROM Ticket t WHERE t.client.id = :clientId")
    Page<Show> findShowsByClientId(@Param("clientId") Long clientId, Pageable pageable);

    @Query("SELECT t.show FROM Ticket t WHERE t.client.login = :clientLogin")
    Page<Show> findShowsByClientLogin(@Param("clientLogin") String clientLogin, Pageable pageable);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.show.date BETWEEN :startDate AND :endDate " +
            "AND t.client.id = :clientId AND t.type.name = :accepted")
    int countTicketsByClientDateBetween(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate,
                                        @Param("clientId") Long clientId,
                                        @Param("accepted") String accepted);

    default int countTicketsByClientDateBetween(LocalDateTime startDate, LocalDateTime endDate, Long clientId) {
        return countTicketsByClientDateBetween(startDate, endDate, clientId, ReservationType.ACCEPTED);
    }

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.show.hall.number = :hallNumber " +
            "AND t.show.date = :date " +
            "AND (t.type.name = :pending OR t.type.name = :accepted)")
    int countOccupiedPlacesByHallNumberDate(@Param("hallNumber") int hallNumber,
                                            @Param("date") LocalDateTime date,
                                            @Param("pending") String pending,
                                            @Param("accepted") String accepted);

    default int countOccupiedPlacesByHallNumberDate(int hallNumber, LocalDateTime date) {
        return countOccupiedPlacesByHallNumberDate(hallNumber, date, ReservationType.PENDING, ReservationType.ACCEPTED);
    }
}