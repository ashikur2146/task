package com.cardinity.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cardinity.project.model.Status;
import com.cardinity.project.model.Task;
import com.cardinity.project.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@RequestMapping(value="", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getAllTasks(final HttpServletRequest httpServletRequest) {
		return ResponseEntity.ok(taskService.getAllTasks());
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> findTask(final HttpServletRequest httpServletRequest, final @PathVariable Long id) {
		return ResponseEntity.ok(taskService.getTaskById(id));
	}
	
	@RequestMapping(value="", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> createTask(final HttpServletRequest httpServletRequest, final @RequestBody Task task) {
		taskService.saveTask(task);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.PUT, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> updateEmployee(final HttpServletRequest httpServletRequest, final @RequestBody Task task) {
		if (task.getStatus() == Status.CLOSED)
			return ResponseEntity.ok("CLOSED TASK CANNOT BE EDITED.");
		taskService.updateTask(task);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> deleteEmployee(final HttpServletRequest httpServletRequest, final @PathVariable Long id) {
		taskService.deleteTask(id);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
