package com.lucas.presbiteriana.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.lucas.presbiteriana.model.entity.Visit;
import com.lucas.presbiteriana.model.repository.UserRepository;
import com.lucas.presbiteriana.model.repository.VisitRepository;
import com.lucas.presbiteriana.service.VisitService;

@Service
public class VisitServiceImpl implements VisitService {

	@Autowired
	private VisitRepository visitRepository;
			
	public VisitServiceImpl(VisitRepository visitRepository) {
		super();
		this.visitRepository = visitRepository;
	}

	
	@Override
	@Transactional
	public Visit saveVisit(Visit visit) {
		return visitRepository.save(visit);
	}

	@Override
	@Transactional
	public Visit updateVisit(Visit visit) {
		Objects.requireNonNull(visit.getId());
		return visitRepository.save(visit);
	}

	@Override
	@Transactional
	public void deleteVisit(Visit visit) {
		Objects.requireNonNull(visit.getId());
		visitRepository.delete(visit);
		
	}

	@Override
	public Optional<Visit> getVisitById(long id) {
		return visitRepository.findById(id);
	}


	@Override
	public List<Visit> searchVisit(Visit visitFilter) {
		 Example example = Example.of(visitFilter, ExampleMatcher.matching()
		 .withIgnoreCase()
		 .withStringMatcher(StringMatcher.CONTAINING));
		 return visitRepository.findAll(example);
	}

}
