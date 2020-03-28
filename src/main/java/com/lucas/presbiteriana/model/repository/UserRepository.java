package com.lucas.presbiteriana.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lucas.presbiteriana.api.dto.UserDto;
import com.lucas.presbiteriana.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
}
