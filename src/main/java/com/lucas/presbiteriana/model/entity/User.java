package com.lucas.presbiteriana.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user", schema = "presbiteriana")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
	
	@Id
	@Column(name = "id")
	@GeneratedValue( strategy = GenerationType.IDENTITY)	
	private Long id;
	
	@Column(name = "name")	
	private String name;
	
	@Column(name = "email")	
	private String email;
	
	@Column(name = "address")	
	private String address;
	
	@Column(name = "age")	
	private Integer age;
	
	@Column(name = "birthday")
	@JsonFormat(pattern="dd/MM/yyyy") 
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)	
	private LocalDate birthday;
	
	@Column(name = "ispresbyter")	
	private Boolean isPresbyter;
	
	@Column(name = "isleader")	
	private Boolean isLeader;
		
	@Column(name = "idleader")	
	private Long idLeader;
	
	@Column(name = "idpresbyter")	
	private Long idPresbyter;		
	
	@Column(name = "phone")	
	private String phone;
	
	@Column(name = "cellphone")	
	private String cellphone;
	
	    	
}
