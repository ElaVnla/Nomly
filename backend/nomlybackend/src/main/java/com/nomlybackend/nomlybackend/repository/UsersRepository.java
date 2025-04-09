package com.nomlybackend.nomlybackend.repository;

import com.nomlybackend.nomlybackend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersRepository extends JpaRepository<Users, Integer> {


}
