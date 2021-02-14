package com.cardinity.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cardinity.data.model.Role;
import com.cardinity.role.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getAllRoles() {
		return ResponseEntity.ok(roleService.getAllRoles());
	}
	
	@RequestMapping(value="/{roleId}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> findRoleById(final @PathVariable Long roleId) {
		return ResponseEntity.ok(roleService.getOneById(roleId));
	}
	
	@RequestMapping(value="", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> createRole(final @RequestBody Role role) {
		roleService.createRole(role);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.PUT, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> updateRole(final @RequestBody Role role) {
		roleService.createRole(role);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete/{roleId}", method=RequestMethod.DELETE, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> deleteRole(final @PathVariable Long roleId) {
		roleService.deleteRole(roleId);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
