package com.cardinity.service.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cardinity.data.model.Project;
import com.cardinity.project.exception.Message;
import com.cardinity.project.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value="/all", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getAllProjects() {
		List<Project> projects = new ArrayList<>();
		try {
			projects = projectService.getAllProjects();
		} catch(Exception e) {
			return new ResponseEntity<Message>(new Message(e.getMessage()), HttpStatus.OK);
		}
		return ResponseEntity.ok(projects);
	}
	
	@RequestMapping(value="/user/{user}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getAllProjectsByUser(final @PathVariable String user) {
		List<Project> projects = new ArrayList<>();
		try {
			projects = projectService.getProjectsByUser(user);
		} catch(Exception e) {
			return new ResponseEntity<Message>(new Message(e.getMessage()), HttpStatus.OK);
		}
		return ResponseEntity.ok(projects);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> findProject(final @PathVariable Long id) {
		Project project = null;
		try {
			project = projectService.findProject(id);
		} catch(Exception e) {
			return new ResponseEntity<Message>(new Message(e.getMessage()), HttpStatus.OK);
		}
		return ResponseEntity.ok(project);
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> createProject(final @RequestBody Project project) {
		try {
			projectService.createProject(project);
		} catch(Exception e) {
			return new ResponseEntity<Message>(new Message(e.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<Message>(new Message("Project creation successful!"), HttpStatus.OK);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.PUT, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> updateProject(final @RequestBody Project project) {
		try {
			projectService.updateProject(project);
		} catch(Exception e) {
			return new ResponseEntity<Message>(new Message(e.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<Message>(new Message("Project update is successful!"), HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete/{projectId}", method=RequestMethod.DELETE, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> deleteProject(final @PathVariable Long projectId) {
		try {
			projectService.deleteProject(projectId);
		} catch(Exception e) {
			return new ResponseEntity<Message>(new Message(e.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<Message>(new Message("Project delete is successful!"), HttpStatus.OK);
	}
}