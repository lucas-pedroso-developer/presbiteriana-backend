package com.lucas.presbiteriana.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "visit", schema = "presbiteriana")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visit implements Serializable {
	
	@Id
	@Column(name = "id")
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "idpresbyter")	
	private Long idPresbyter;
	
	@Column(name = "idleader")	
	private Long idLeader;		
	
	@Column(name = "visitdate")	
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate visitDate;	
	
	@Column(name = "presbytername")	
	private String presbyterName;
	
	@Column(name = "leadername")	
	private String leaderName;			
			
}
