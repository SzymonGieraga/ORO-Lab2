package org.example.theater.repo;


import org.example.theater.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepo extends JpaRepository<Hall, Long> {

}