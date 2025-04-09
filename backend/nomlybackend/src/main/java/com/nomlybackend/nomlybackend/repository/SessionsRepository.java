package com.nomlybackend.nomlybackend.repository;

import com.nomlybackend.nomlybackend.model.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionsRepository extends JpaRepository<Sessions, Integer> {
}
