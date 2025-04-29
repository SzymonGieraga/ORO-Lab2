package org.example.theater.repo;

import org.example.theater.model.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketTypeRepo extends JpaRepository<TicketType, Long> {
    @Query("SELECT tt FROM TicketType tt WHERE tt.name = :name")
    TicketType findByName(@Param("name") String name);
}