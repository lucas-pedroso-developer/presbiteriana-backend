package com.lucas.presbiteriana.service;

import java.util.List;
import java.util.Optional;
import com.lucas.presbiteriana.model.entity.Visit;

public interface VisitService {
	Visit saveVisit(Visit visit);	
	Visit updateVisit(Visit visit);
	void deleteVisit(Visit visit);
	Optional<Visit> getVisitById(long id);
	List<Visit> searchVisit(Visit visitFilter);	
}
