package org.example.theater.repo;

import org.example.theater.model.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShowRepo extends JpaRepository<Show, Long> {
    Page<Show> findByHallId(Long hallId, Pageable pageable);

    Page<Show> findByPlayId(Long playId, Pageable pageable);

    Page<Show> findByPlayName(String playName, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT s.hall.number) FROM Show s WHERE s.play.id = :playId")
    int countHallsByPlayId(@Param("playId") Long playId);
}