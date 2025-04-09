package com.nomlybackend.nomlybackend.repository;

import com.nomlybackend.nomlybackend.model.UsersGroupings;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersGroupingsRepository extends JpaRepository<UsersGroupings, Integer> {

}
