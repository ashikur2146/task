package com.cardinity.role.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardinity.data.model.Role;
import com.cardinity.data.repository.RoleRepository;
import com.cardinity.project.exception.CustomException;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role getOneById(Long id) {
		Role role = roleRepository.getOne(id);
		if(role == null)
			throw new CustomException("Role Not Found!");
		return role;
	}

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Role createRole(Role role) {
		return roleRepository.saveAndFlush(role);
	}

	@Override
	public Role updateRole(Role role) {
		return roleRepository.saveAndFlush(role);
	}

	@Override
	public void deleteRole(Long id) {
		Role role = roleRepository.getOne(id);
		if(role == null)
			throw new CustomException("Role Not Found!");
		roleRepository.delete(role);
	}

	@Override
	public Role getByRoleName(String roleName) {
		Role role = roleRepository.findAll().parallelStream().filter(r -> r.getRoleName().equals(roleName)).findFirst().get();
		if (role == null)
			throw new CustomException("Role not found!");
		return role;
	}

}
