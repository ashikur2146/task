package com.cardinity.service.controller;

import java.util.ArrayList;
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

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> getAllTasks() {
		List<Task> tasks = new ArrayList<>();
		try {
			tasks = taskService.getAllTasks();
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message(e.getMessage()), HttpStatus.OK);
		}
		return ResponseEntity.ok(tasks);
	}

	@RequestMapping(value = "/user/{name}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> getAllTasksByUserName(final @PathVariable @NotEmpty @NotNull String name) {
		List<Task> tasks = new ArrayList<>();
		try {
			tasks = taskService.getAllTasksByUser(name);
			if (tasks.size() < 1)
				return new ResponseEntity<Message>(new Message("User has no tasks!"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message(e.getMessage()), HttpStatus.OK);
		}
		return ResponseEntity.ok(tasks);
	}

	/*
	 * @RequestMapping(value="/{id}", method=RequestMethod.GET,
	 * produces="application/json")
	 * 
	 * @ResponseBody public ResponseEntity<?>
	 * getAllTasksById(final @PathVariable @NotNull Long id) { List<Task> tasks =
	 * taskService.getAllTasks().parallelStream() .filter(t -> t.getUser().getId()
	 * == id).collect(Collectors.toList()); if (tasks.size() < 1) return new
	 * ResponseEntity<Message>(new Message("User has no tasks!"), HttpStatus.OK);
	 * return ResponseEntity.ok(tasks); }
	 */

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> findTask(final @PathVariable @NotNull Long id) {
		Task task = null;
		try {
			task = taskService.getTaskById(id);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message(e.getMessage()), HttpStatus.OK);
		}
		return ResponseEntity.ok(task);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> createTask(final @RequestBody @NotNull Task task) {
		try {
			taskService.createTask(task);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message(e.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<Message>(new Message("Task creation is successful!"), HttpStatus.OK);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> updateTask(final @RequestBody @NotNull Task task, final @PathVariable @NotNull Long id) {
		try {
			taskService.updateTask(id, task);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message(e.getMessage()), HttpStatus.OK);
		}
		return new ResponseEntity<Message>(new Message("Task updated successfully!"), HttpStatus.OK);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> deleteTask(final @PathVariable Long id) {
		try {
			taskService.deleteTask(id);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message(e.getMessage()), HttpStatus.OK);
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@RequestMapping(value = "/project/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> getTasksByProjectId(final @PathVariable Long id) {
		List<Task> tasks = new ArrayList<>();
		try {
			tasks = taskService.getTasksByProjectId(id);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message(e.getMessage()), HttpStatus.OK);
		}
		return ResponseEntity.ok(tasks);
	}

	/*
	 * @RequestMapping(value="/title/{title}", method=RequestMethod.GET,
	 * produces="application/json")
	 * 
	 * @ResponseBody public ResponseEntity<?>
	 * getTasksByProjectTitle(final @PathVariable String title) { List<Task> tasks =
	 * new ArrayList<>(); try { tasks = taskService.getTasksByProjectTitle(title); }
	 * catch(Exception e) { return new ResponseEntity<Message>(new
	 * Message(e.getMessage()), HttpStatus.OK); } return ResponseEntity.ok(tasks); }
	 */

	@RequestMapping(value = "/status/{status}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> getTasksByStatus(final @PathVariable Status status) {
		List<Task> tasks = new ArrayList<>();
		try {
			tasks = taskService.getTasksByStatus(status);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message(e.getMessage()), HttpStatus.OK);
		}
		return ResponseEntity.ok(tasks);
	}

	@RequestMapping(value = "/due/{date}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> getTasksByProjectId(final @PathVariable Date dueDate) {
		List<Task> tasks = new ArrayList<>();
		try {
			tasks = taskService.getTasksByDueDate(dueDate);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message(e.getMessage()), HttpStatus.OK);
		}
		return ResponseEntity.ok(tasks);
	}
}