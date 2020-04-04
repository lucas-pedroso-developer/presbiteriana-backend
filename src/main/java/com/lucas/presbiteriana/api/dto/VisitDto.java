package com.lucas.presbiteriana.api.dto;

import java.time.LocalDate;

import javax.persistence.Convert;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.fasterxml.jackson.annotation.JsonFormat;

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
		
	@JsonFormat(pattern="dd/MM/yyyy")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate visitDate;
	
	private LocalDate visitDateConverted;
	
	private String presbyterName;
	
	private String leaderName;
	
}
