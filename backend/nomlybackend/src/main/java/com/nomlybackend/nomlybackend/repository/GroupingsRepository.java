package com.nomlybackend.nomlybackend.repository;

import com.nomlybackend.nomlybackend.model.Groupings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GroupingsRepository extends JpaRepository<Groupings, Integer> {

    Groupings findByGroupCode(String groupCode);




}
