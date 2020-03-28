package com.lucas.presbiteriana.service;

import java.util.List;
import java.util.Optional;
import com.lucas.presbiteriana.model.entity.User;

public interface UserService {
	User saveUser(User user);	
	User updateUser(User user);
	void deleteUser(User user);
	Optional<User> getUserById(long id);	
	List<User> searchUser(User userFilter);
}
