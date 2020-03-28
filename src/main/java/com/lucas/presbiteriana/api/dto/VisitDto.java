package com.lucas.presbiteriana.api.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitDto {

	private Long id;
	
	private Long idPresbyter;
	
	private Long idLeader;
		
	private LocalDate visitDate;
	
	private LocalDate visitDateConverted;
	
	private String presbyterName;
	
	private String leaderName;
	
}
