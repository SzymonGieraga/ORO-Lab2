package org.example.theater.repo;

import org.example.theater.model.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShowRepo extends JpaRepository<Show, Long> {
    @Query("SELECT s FROM Show s WHERE s.hall.id = :hallId")
    Page<Show> findByHallId(@Param("hallId") Long hallId, Pageable pageable);

    @Query("SELECT s FROM Show s WHERE s.play.id = :playId")
    Page<Show> findByPlayId(@Param("playId") Long playId, Pageable pageable);

    @Query("SELECT s FROM Show s WHERE s.play.name = :playName")
    Page<Show> findByPlayName(@Param("playName") String playName, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT s.hall.number) FROM Show s WHERE s.play.id = :playId")
    int countHallsByPlayId(@Param("playId") Long playId);
}