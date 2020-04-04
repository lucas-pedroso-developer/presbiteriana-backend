package com.lucas.presbiteriana.api.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lucas.presbiteriana.api.dto.UserDto;
import com.lucas.presbiteriana.exception.BusinessRuleException;
import com.lucas.presbiteriana.model.entity.User;
import com.lucas.presbiteriana.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private UserService service;
	
	public UserController(UserService service) {
		this.service = service;
	}
	
	@PostMapping("")
	public ResponseEntity save(@RequestBody UserDto dto) {		
		User user = User
				.builder()
				.name(dto.getName())
				.email(dto.getEmail())
				.address(dto.getAddress())
				.age(dto.getAge())
				.birthday(dto.getBirthday())
				.isPresbyter(dto.getIsPresbyter())
				.isLeader(dto.getIsLeader())
				.idLeader(dto.getIdLeader())
				.idPresbyter(dto.getIdPresbyter())
				.phone(dto.getPhone())
				.cellphone(dto.getCellphone())
				.build();
		System.out.print(user);
		
		try {
			User savedUser = service.saveUser(user);
			return new ResponseEntity(user, HttpStatus.CREATED);
		} catch (BusinessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}				
	}
	
	@GetMapping("{id}")
	public ResponseEntity getUserById(@PathVariable("id") Long id) {
		return service.getUserById(id)
				.map(user -> new ResponseEntity(converterDto(user), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
	}
	
	@PutMapping("{id}")
	public ResponseEntity update(@PathVariable("id") Long id, @RequestBody UserDto dto) {		
		return service.getUserById(id).map( entity -> {
			try {
				User user = convert(dto);
				user.setId(entity.getId());				
				service.updateUser(user);				
				return ResponseEntity.ok(user);
			} catch (BusinessRuleException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("erro", HttpStatus.BAD_REQUEST));		
	}
			
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar( @PathVariable("id") Long id ) {
		return service.getUserById(id).map(entity -> {
			service.deleteUser(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de Dados", HttpStatus.BAD_REQUEST) );
	}
	
	@GetMapping
	public ResponseEntity search(
			@RequestParam(value = "id", required = false) Long userId,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "age", required = false) Integer age,			
			@RequestParam(value = "birthday", required = false) @DateTimeFormat(pattern="dd/MM/yyyy")  LocalDate birthday,								   
			@RequestParam(value = "isPresbyter", required = false) Boolean isPresbyter,
			@RequestParam(value = "isLeader", required = false) Boolean isLeader,
			@RequestParam(value = "idLeader", required = false) Long idLeader,
			@RequestParam(value = "idPresbyter", required = false) Long idPresbyter,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "cellphone", required = false) String cellphone
			
			) {
		User userFilter = new User();
		userFilter.setName(name); 
		userFilter.setEmail(email);
		userFilter.setAddress(address);
		userFilter.setAge(age);				
		userFilter.setBirthday(birthday);
		userFilter.setIsPresbyter(isPresbyter);
		userFilter.setIsLeader(isLeader);
		userFilter.setIdLeader(idLeader);
		userFilter.setIdPresbyter(idPresbyter);
		userFilter.setPhone(phone);
		userFilter.setCellphone(cellphone);
						
		List<User> users = service.searchUser(userFilter);		
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/leaders")
	public ResponseEntity getAllLeaders() {
		User userFilter = new User();		
		userFilter.setIsLeader(true);
		List<User> users = service.searchUser(userFilter);
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/presbyters")
	public ResponseEntity getAllPresbyters() {
		User userFilter = new User();		
		userFilter.setIsPresbyter(true);
		List<User> users = service.searchUser(userFilter);
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/dependents")
	public ResponseEntity getAllDependents() {
		User userFilter = new User();		
		userFilter.setIsLeader(false);
		List<User> users = service.searchUser(userFilter);
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/dependentsByLeader/{id}")
	public ResponseEntity getDependentsByLeader( @PathVariable("id") Long id ) {
		User userFilter = new User();		
		userFilter.setIdLeader(id);
		List<User> users = service.searchUser(userFilter);
		return ResponseEntity.ok(users);
	}
		
	private User convert(UserDto dto) {
		User user = new User();
		user.setId(dto.getId());
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setAddress(dto.getAddress());
		user.setAge(dto.getAge());
		user.setBirthday(dto.getBirthday());
		user.setIsPresbyter(dto.getIsPresbyter());
		user.setIsLeader(dto.getIsLeader());
		user.setIdLeader(dto.getIdLeader());
		user.setIdPresbyter(dto.getIdPresbyter());
		user.setPhone(dto.getPhone());
		user.setCellphone(dto.getCellphone());
						
		return user;	
	}
	
	private UserDto converterDto(User user) {
		return UserDto.builder()	
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.address(user.getAddress())
				.age(user.getAge())
				.birthday(user.getBirthday())
				.isPresbyter(user.getIsPresbyter())
				.isLeader(user.getIsLeader())	
				.idLeader(user.getIdLeader())
				.idPresbyter(user.getIdPresbyter())
				.phone(user.getPhone())
				.cellphone(user.getCellphone())
				.build();		
	}
	
	private LocalDate convertToLocalDate(String date) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
		String dateStr = localDate.format(dateTimeFormatter);
		return LocalDate.parse(dateStr);
	}
	
}
