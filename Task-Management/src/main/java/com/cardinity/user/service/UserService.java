package com.cardinity.user.service;

import java.util.List;

import com.cardinity.data.model.User;

public interface UserService {
	public User getOneById(Long id);
	public User getUserByName(String userName);
	public List<User> getAllUsers();
	public User createUser(String userName, String passWord);
	public User updateUser(User user);
	public void deleteUser(Long id);
	public User addRoleToUser(String userName, String roleName);
}