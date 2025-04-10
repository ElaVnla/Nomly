package com.nomlybackend.nomlybackend.repository;

import com.nomlybackend.nomlybackend.model.eateries.Eateries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EateryRepository extends JpaRepository<Eateries, Integer> {
}
