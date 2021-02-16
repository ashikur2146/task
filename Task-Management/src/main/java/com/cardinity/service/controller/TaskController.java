package com.cardinity.service.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cardinity.data.model.Status;
import com.cardinity.data.model.Task;
import com.cardinity.project.exception.Message;
import com.cardinity.task.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
		
	private static final String CLOSED_STATUS = "CLOSED";
	private static final String CLOSED_STATUS_EDIT_MESSAGE = "CLOSED TASK CANNOT BE EDITED.";
	
	@RequestMapping(value="/all-tasks", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getAllTasks() {
		return ResponseEntity.ok(taskService.getAllTasks());
	}
	
	@RequestMapping(value="/all-tasks/user/{name}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getAllTasksByUserName(final @PathVariable @NotEmpty @NotNull String username) {
		List<Task> tasks = taskService.getAllTasks().parallelStream()
				.filter(t -> t.getUser().getUserName().equalsIgnoreCase(username)).collect(Collectors.toList()); 
		if (tasks.size() < 1)
			return new ResponseEntity<Message>(new Message("User has no tasks!"), HttpStatus.OK);
		return ResponseEntity.ok(tasks);
	}
	
	@RequestMapping(value="/all-tasks/user/{id}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getAllTasksById(final @PathVariable @NotNull Long id) {
		List<Task> tasks = taskService.getAllTasks().parallelStream()
				.filter(t -> t.getUser().getId() == id).collect(Collectors.toList()); 
		if (tasks.size() < 1)
			return new ResponseEntity<Message>(new Message("User has no tasks!"), HttpStatus.OK);
		return ResponseEntity.ok(tasks);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> findTask(final @PathVariable @NotNull Long id) {
		return ResponseEntity.ok(taskService.getTaskById(id));
	}
	
	@RequestMapping(value="/create-task", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> createTask(final @RequestBody @NotNull Task task) {
		try {
			taskService.createTask(task);
		} catch(Exception e) {
			return new ResponseEntity<Message>(new Message(e.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<Message>(new Message("Task creation is successful!"), HttpStatus.OK);
	}
	
	@RequestMapping(value="/update-task", method=RequestMethod.PUT, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> updateEmployee(final @RequestBody @NotNull Task task) {
		if (task.getStatus().equals(CLOSED_STATUS))
			return new ResponseEntity<Message>(new Message(CLOSED_STATUS_EDIT_MESSAGE), HttpStatus.OK);
		taskService.updateTask(task);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE, consumes="application/json", produces="application/json")
	@ResponseBody
	public ResponseEntity<?> deleteEmployee(final @PathVariable Long id) {
		taskService.deleteTask(id);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@RequestMapping(value="/project/{id}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getTasksByProjectId(final @PathVariable Long id) {
		return ResponseEntity.ok(taskService.getTasksByProjectId(id));
	}
	
	@RequestMapping(value="/project/title/{title}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getTasksByProjectTitle(final @PathVariable String title) {
		return ResponseEntity.ok(taskService.getTasksByProjectTitle(title));
	}
	
	@RequestMapping(value="/status/{status}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getTasksByStatus(final @PathVariable Status status) {
		return ResponseEntity.ok(taskService.getTasksByStatus(status));
	}
	
	@RequestMapping(value="/due/{date}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<?> getTasksByProjectId(final @PathVariable Date dueDate) {
		return ResponseEntity.ok(taskService.getTasksByDueDate(dueDate));
	}
}