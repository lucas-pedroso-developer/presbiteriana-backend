package com.lucas.presbiteriana.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.presbiteriana.model.entity.User;
import com.lucas.presbiteriana.model.entity.Visit;

import java.util.List;

import org.springframework.data.domain.Sort;

public interface VisitRepository extends JpaRepository<Visit, Long> {
		
}
