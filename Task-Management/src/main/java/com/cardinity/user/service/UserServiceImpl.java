package com.cardinity.user.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cardinity.data.model.Project;
import com.cardinity.data.model.Role;
import com.cardinity.data.model.Task;
import com.cardinity.data.model.User;
import com.cardinity.data.repository.UserRepository;
import com.cardinity.project.exception.CustomException;
import com.cardinity.role.service.RoleService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleService roleService;
	
	private static final String ADMIN_ROLE = "ADMIN";

	@Override
	public User getOneById(Long id) {
		User user = userRepository.getOne(id);
		if (user == null)
			throw new CustomException("User not found!");
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User createUser(String userName, String passWord) {
		User findUser = this.getAllUsers().parallelStream().filter(u -> u.getUserName().equals(userName)).findFirst()
				.orElse(null);
		if (findUser != null)
			throw new CustomException("User already found in the system!");
		User user = new User();
		user.setUserName(userName);
		user.setPassword(bCryptPasswordEncoder().encode(passWord));
		user.setActivated(true);
		userRepository.saveAndFlush(user);
		addRoleToUser(user.getUserName(), "USER");
		return user;
	}

	@Override
	public User updateUser(User user) {
		return userRepository.saveAndFlush(user);
	}

	@Override
	public void deleteUser(Long id) {
		User user = userRepository.getOne(id);
		if (user == null)
			throw new CustomException("User not found!");
		userRepository.delete(user);
	}

	@Override
	public User addRoleToUser(String userName, String roleName) {
		Role role = roleService.getByRoleName(roleName);
		User user = this.getUserByName(userName);
		user.getRoles().add(role);
		return user;
	}

	@Override
	public User getUserByName(String userName) {
		User user = userRepository.findAll().parallelStream().filter(u -> u.getUserName().equals(userName)).findFirst()
				.get();
		if (user == null)
			throw new CustomException("User not found!");
		return user;
	}
	
	@Override
	public User addTaskToUser(String userName, Task task) {
		User user = this.getUserByName(userName);
		user.getTasks().add(task);
		return user;
	}

	@Override
	public User addProjectToUser(String userName, Project project) {
		User user = this.getUserByName(userName);
		user.getProjects().add(project);
		return user;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public User getAuthenticatedUser() {
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		User user = this.getUserByName(username);
		return user;
	}

	@Override
	public boolean isAdmin(User user) {
		return user.getRoles().parallelStream().anyMatch(role -> role.getRoleName().equals(ADMIN_ROLE));
	}
}
