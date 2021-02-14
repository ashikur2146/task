package com.cardinity.user.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardinity.data.model.Role;
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
	public User createUser(User user) {
		return userRepository.saveAndFlush(user);
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
		User user = userRepository.findAll().parallelStream().filter(u -> u.getUserName().equals(userName)).findFirst().get();
		if (user == null)
			throw new CustomException("User not found!");
		return user;
	}

}
