package com.nomlybackend.nomlybackend.repository;

import com.nomlybackend.nomlybackend.model.SessionsEateries;
import com.nomlybackend.nomlybackend.model.Users;
import com.nomlybackend.nomlybackend.model.UsersSessionsEateries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersSessionsEateriesRepository extends JpaRepository<UsersSessionsEateries, Integer> {
    List<UsersSessionsEateries> findByEateryEateryId(String eateryId);

    List<UsersSessionsEateries> findBySessionSessionId(int sessionId);
}
