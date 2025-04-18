package com.nomlybackend.nomlybackend.repository;

import com.nomlybackend.nomlybackend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UsersRepository extends JpaRepository<Users, Integer> {

    @Query(value = """
    SELECT u.*
    FROM Users u
    JOIN UsersSessionsEateries usea ON u.UserId = usea.UserId
    WHERE usea.SessionId = :sessionId
    GROUP BY u.UserId
    HAVING COUNT(DISTINCT usea.EateryId) = (
        SELECT COUNT(DISTINCT se.EateryId)
        FROM SessionsEateries se
        WHERE se.SessionId = :sessionId
    )
    """, nativeQuery = true)
    List<Users> findUsersWithAllEateriesInSession(@Param("sessionId") int sessionId);
}
