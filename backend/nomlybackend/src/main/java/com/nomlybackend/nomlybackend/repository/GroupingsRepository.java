package com.nomlybackend.nomlybackend.repository;

import com.nomlybackend.nomlybackend.model.Groupings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface GroupingsRepository extends JpaRepository<Groupings, Integer> {

    Optional<Groupings> findByGroupCode(String groupCode);
}
