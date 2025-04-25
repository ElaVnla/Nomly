package com.nomlybackend.nomlybackend.repository;

import com.nomlybackend.nomlybackend.model.SessionsEateries;
import com.nomlybackend.nomlybackend.model.Users;
import com.nomlybackend.nomlybackend.model.UsersSessionsEateries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersSessionsEateriesRepository extends JpaRepository<UsersSessionsEateries, Integer> {
    List<UsersSessionsEateries> findByEateryEateryId(String eateryId);

    List<UsersSessionsEateries> findBySessionSessionId(int sessionId);

    //Query to retrieve all users based on sessionId
    @Query("SELECT u.preferences FROM Users u JOIN UsersSessionsEateries usee ON u = usee.user WHERE usee.session.sessionId = :sessionId")
    List<String> findPreferencesBySessionId(@Param("sessionId") Integer sessionId);
}
