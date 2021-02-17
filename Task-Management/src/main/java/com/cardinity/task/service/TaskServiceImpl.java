package com.cardinity.task.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardinity.data.model.Project;
import com.cardinity.data.model.Status;
import com.cardinity.data.model.Task;
import com.cardinity.data.model.User;
import com.cardinity.data.repository.TaskRepository;
import com.cardinity.project.exception.CustomException;
import com.cardinity.project.service.ProjectService;
import com.cardinity.user.service.UserService;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;

	@Override
	public Task getTaskById(Long id) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException("User is not logged in.");
		Task task = taskRepository.getOne(id);
	    if (task == null)
			throw new CustomException("Task does not exist");
		if (!task.getUser().getUserName().equals(user.getUserName()))
			throw new CustomException("User doesn't have access right to view this task.");
		return task;
	}

	@Override
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	@Override
	public Task createTask(Task task) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException("Login before creating a task");
		task.setUser(user);
		String projectTitle = task.getProject().getTitle();
		Project project = projectService.findProjectByTitle(projectTitle);
		if (project == null)
			throw new CustomException(projectTitle + " project not found.");
		task.setProject(project);
		return taskRepository.saveAndFlush(task);
	}

	@Override
	public Task updateTask(Task task) {
		return taskRepository.saveAndFlush(task);
	}

	@Override
	public void deleteTask(Long id) {
		Task task = this.getTaskById(id);
		taskRepository.delete(task);
	}

	@Override
	public List<Task> getTasksByProjectId(Long projectId) {
		return this.getAllTasks().parallelStream().filter(task -> task.getProject().getId() == projectId)
				.collect(Collectors.toList());
	}

	@Override
	public List<Task> getTasksByProjectTitle(String projectTitle) {
		return this.getAllTasks().parallelStream().filter(task -> task.getProject().getTitle().equalsIgnoreCase(projectTitle))
				.collect(Collectors.toList());
	}

	@Override
	public List<Task> getTasksByStatus(Status status) {
		return this.getAllTasks().parallelStream().filter(task -> task.getStatus().equalsIgnoreCase(status.getStatus()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Task> getTasksByDueDate(Date dueDate) {
		return this.getAllTasks().parallelStream().filter(task -> task.getEndDate() == dueDate)
				.collect(Collectors.toList());
	}
}
