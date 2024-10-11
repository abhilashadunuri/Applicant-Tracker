package com.example.ui.DAO;

import java.util.List;

import com.example.ui.domain.User;

public interface UserDAO {
	void saveUser(User user);
	void deleteUser(User user);
	void updateUser(User user);
	User getUserById(Long id);
	List<User> findAllUsers();
	User findByUsername(String username);
	void saveCandidate(User user);
}
