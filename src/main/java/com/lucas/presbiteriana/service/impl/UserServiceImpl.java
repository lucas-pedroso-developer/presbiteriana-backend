package com.lucas.presbiteriana.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.lucas.presbiteriana.model.entity.User;
import com.lucas.presbiteriana.model.repository.UserRepository;
import com.lucas.presbiteriana.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
			
	public UserServiceImpl(UserRepository userRepository) {
		//super();
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public User saveUser(User user) {		
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public User updateUser(User user) {		
		Objects.requireNonNull(user.getId());
		return userRepository.save(user);
	}
			
	@Override
	@Transactional
	public void deleteUser(User user) {
		Objects.requireNonNull(user.getId());
		userRepository.delete(user);		
	}
	
	@Override
	public Optional<User> getUserById(long id) {
		return userRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> searchUser(User userFilter) {
		Example example = Example.of(userFilter, ExampleMatcher.matching()
				 .withIgnoreCase()
				 .withStringMatcher(StringMatcher.CONTAINING));
		return userRepository.findAll(example);
	}
	
	


}
