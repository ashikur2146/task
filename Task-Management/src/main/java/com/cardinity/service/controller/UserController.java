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

import com.cardinity.data.model.User;
import com.cardinity.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@RequestMapping(value="/{UserId}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> findUserById(final @PathVariable Long userId) {
		return ResponseEntity.ok(userService.getOneById(userId));
	}
	
	@RequestMapping(value="", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> createUser(final @RequestBody User user) {
		userService.createUser(user);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.PUT, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> updateUser(final @RequestBody User user) {
		userService.createUser(user);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete/{UserId}", method=RequestMethod.DELETE, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> deleteUser(final @PathVariable Long userId) {
		userService.deleteUser(userId);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
