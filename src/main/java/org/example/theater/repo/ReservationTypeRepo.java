package org.example.theater.repo;

import org.example.theater.model.ReservationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationTypeRepo extends JpaRepository<ReservationType, Long> {
    @Query("SELECT rt FROM ReservationType rt WHERE rt.name = :name")
    ReservationType findByName(@Param("name") String name);
}