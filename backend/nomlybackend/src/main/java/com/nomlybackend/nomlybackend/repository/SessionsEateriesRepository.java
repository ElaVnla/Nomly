package com.nomlybackend.nomlybackend.repository;

import com.nomlybackend.nomlybackend.model.SessionsEateries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionsEateriesRepository extends JpaRepository<SessionsEateries, String> {

    List<SessionsEateries> findByEateryEateryId(String eateryId);
}
