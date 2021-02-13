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

import com.cardinity.data.model.Project;
import com.cardinity.project.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value="", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getAllProjects() {
		return ResponseEntity.ok(projectService.getAllProjects());
	}
	
	@RequestMapping(value="/{projectId}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> findProject(final @PathVariable Long projectId) {
		return ResponseEntity.ok(projectService.findProject(projectId));
	}
	
	@RequestMapping(value="", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> createProject(final @RequestBody Project project) {
		projectService.createProject(project);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.PUT, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> updateProject(final @RequestBody Project project) {
		projectService.updateProject(project);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete/{projectId}", method=RequestMethod.DELETE, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> deleteProject(final @PathVariable Long projectId) {
		projectService.deleteProject(projectId);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
