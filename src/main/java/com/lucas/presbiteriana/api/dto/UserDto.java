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
public class UserDto {
	
	private Long id;
	
	private String name;
		
	private String email;
		
	private String address;
		
	private Integer age;
			
	@JsonFormat(pattern="dd/MM/yyyy")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate birthday;
	
	private LocalDate birthdayConverted;
		
	private Boolean isPresbyter;
		
	private Boolean isLeader;
	
	private Long idLeader;
	
	private Long idPresbyter;
	
	private String phone;
	
	private String cellphone;
	
}
