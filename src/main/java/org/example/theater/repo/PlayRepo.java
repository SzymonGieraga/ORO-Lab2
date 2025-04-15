package org.example.theater.repo;

import org.example.theater.model.Play;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayRepo extends JpaRepository<Play, Long> {

}