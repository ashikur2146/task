package com.cardinity.role.service;

import java.util.List;

import com.cardinity.data.model.Role;

public interface RoleService {
	public Role getOneById(Long id);
	public Role getByRoleName(String roleName);
	public List<Role> getAllRoles();
	public Role createRole(Role role);
	public Role updateRole(Role role);
	public void deleteRole(Long id);
}
