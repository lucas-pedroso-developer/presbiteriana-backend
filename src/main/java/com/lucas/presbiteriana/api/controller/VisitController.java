package com.lucas.presbiteriana.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.presbiteriana.api.dto.UserDto;
import com.lucas.presbiteriana.api.dto.VisitDto;
import com.lucas.presbiteriana.exception.BusinessRuleException;
import com.lucas.presbiteriana.model.entity.User;
import com.lucas.presbiteriana.model.entity.Visit;
import com.lucas.presbiteriana.service.UserService;
import com.lucas.presbiteriana.service.VisitService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/visit")
public class VisitController {

	private VisitService service;
	
	public VisitController(VisitService service) {
		this.service = service;
	}
	
	@GetMapping("{id}")
	public ResponseEntity getVisitById(@PathVariable("id") Long id) {
		return service.getVisitById(id)
				.map(visit -> new ResponseEntity(converterDto(visit), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping("")
	public ResponseEntity save(@RequestBody VisitDto dto) {
		Visit visit = Visit
				.builder()
				.idPresbyter(dto.getIdPresbyter())
				.idLeader(dto.getIdLeader())
				.visitDate(dto.getVisitDate())
				.presbyterName(dto.getPresbyterName())
				.leaderName(dto.getLeaderName())
				.build();

		try {
			Visit savedVisit = service.saveVisit(visit);
			return new ResponseEntity(savedVisit, HttpStatus.CREATED);
		} catch (BusinessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}				
	}
	
	@PutMapping("{id}")
	public ResponseEntity update(@PathVariable("id") Long id, @RequestBody VisitDto dto) {		
		return service.getVisitById(id).map( entity -> {
			try {
				Visit visit = convert(dto);
				visit.setId(entity.getId());
				service.updateVisit(visit);
				return ResponseEntity.ok(visit);
			} catch (BusinessRuleException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("erro", HttpStatus.BAD_REQUEST));		
	}
			
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar( @PathVariable("id") Long id ) {
		return service.getVisitById(id).map(entity -> {
			service.deleteVisit(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Visita não encotrada!", HttpStatus.BAD_REQUEST) );
	}
	
	@GetMapping
	public ResponseEntity search(
			@RequestParam(value = "id", required = false) Long visitId,
			@RequestParam(value = "idleader", required = false) Long idLeader,
			@RequestParam(value = "idPresbyter", required = false) Long idPresbyter,
			@RequestParam(value = "visitDate", required = false) @DateTimeFormat(pattern="dd/MM/yyyy")  LocalDate visitDate,			
			@RequestParam(value = "leaderName", required = false) Long leaderName,
			@RequestParam(value = "presbyterName", required = false) Long presbyterName
			) {
		
		Visit visitFilter = new Visit();
		visitFilter.setIdPresbyter(idPresbyter); 
		visitFilter.setIdLeader(idLeader);
		visitFilter.setVisitDate(visitDate);
		/*if(visitDate != null) {
			LocalDate visitLocalDate = LocalDate.parse(visitDate);
			visitFilter.setVisitDate(visitLocalDate);
		}*/			
		List<Visit> visits = service.searchVisit(visitFilter);
		return ResponseEntity.ok(visits);
	}
		
	private Visit convert(VisitDto dto) {
		Visit visit = new Visit();
		visit.setId(dto.getId());
		visit.setIdPresbyter(dto.getIdPresbyter());
		visit.setIdLeader(dto.getIdLeader());
		visit.setVisitDate(dto.getVisitDate());
		visit.setLeaderName(dto.getLeaderName());
		visit.setPresbyterName(dto.getPresbyterName());
				
		service
			.getVisitById(dto.getId())
			.orElseThrow(() -> new BusinessRuleException("Visita não encontrada!"));
		
		return visit;	
	}
			
	private VisitDto converterDto(Visit visit) {
		return VisitDto.builder()	
				.id(visit.getId())
				.idPresbyter(visit.getIdPresbyter())
				.idLeader(visit.getIdLeader())
				.presbyterName(visit.getPresbyterName())
				.leaderName(visit.getLeaderName())
				.visitDate(visit.getVisitDate())
				.build();		
	}
			
	private LocalDate convertStringToDate(String dtoDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();		
		LocalDate visitLocalDate = null;
		DateTimeFormatter formatter;
		try {
			date = sdf.parse(dtoDate);
			sdf = new SimpleDateFormat("yyyy-MM-dd");		
			String sdfString = sdf.toString();	  
			formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");	        
			return visitLocalDate = LocalDate.parse(sdfString, formatter);	  
		 } catch (ParseException e) {
		  e.printStackTrace();
		 }
		return null;	
	}
	
}
