package com.cardinity.user.service;

import java.util.List;

import com.cardinity.data.model.Project;
import com.cardinity.data.model.Task;
import com.cardinity.data.model.User;

public interface UserService {
	public User getOneById(Long id);
	public User getUserByName(String userName);
	public List<User> getAllUsers();
	public User createUser(String userName, String passWord);
	public User updateUser(User user);
	public void deleteUser(Long id);
	public User addRoleToUser(String userName, String roleName);
	public User addTaskToUser(String userName, Task task);
	public User addProjectToUser(String userName, Project project);
	public User getAuthenticatedUser();
	public boolean isAdmin(User user);
}