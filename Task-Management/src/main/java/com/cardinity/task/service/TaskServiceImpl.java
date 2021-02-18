package com.cardinity.task.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	
	private static final String AUTHENTICATION_MESSAGE = "USER AUTHENTICATION IS REQUIRED!";
	private static final String TASK_MESSAGE = "Task does not exist.";
	private static final String PROJECT_MESSAGE = "Project does not exist.";
	private static final String USER_MESSAGE = "User does not have access right.";
	private static final String DUPLICATE_TASK_MESSAGE = "task name is duplicate";
	private static final String DUE_DATE_MESSAGE = "Date should be in the past.";
	
	private static final String CLOSED_STATUS = "CLOSED";
	private static final String CLOSED_STATUS_EDIT_MESSAGE = "CLOSED TASK CANNOT BE EDITED.";
	
	@Override
	public Task getTaskById(Long id) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException("User is not logged in.");
		Task task = taskRepository.getOne(id);
		boolean isAdmin = userService.isAdmin(user);
	    if (task == null)
			throw new CustomException("Task does not exist");
		if (!task.getUser().getUserName().equals(user.getUserName()) && !isAdmin)
			throw new CustomException("User doesn't have access right to view this task.");
		return task;
	}

	@Override
	public List<Task> getAllTasks() {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		boolean isAdmin = userService.isAdmin(user);
		if (isAdmin)
			return taskRepository.findAll();
		return taskRepository.findAll().parallelStream()
				.filter(t -> t.getUser().getUserName().equals(user.getUserName())).collect(Collectors.toList());
	}

	@Override
	public Task createTask(Task task) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		task.setUser(user);
		Project project = projectService.findProjectByTitle(task.getProject().getName());
		if (project == null)
			throw new CustomException(PROJECT_MESSAGE);
		task.setProject(project);
		return taskRepository.saveAndFlush(task);
	}

	

	@Override
	public void deleteTask(Long id) {
		Task task = this.getTaskById(id);
		taskRepository.delete(task);
	}

	@Override
	public List<Task> getTasksByProjectId(Long projectId) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		boolean isAdmin = userService.isAdmin(user);
		if (isAdmin)
			return taskRepository.findAll().parallelStream().filter(task -> task.getProject().getId() == projectId)
				.collect(Collectors.toList());
		return taskRepository.findAll().parallelStream().filter(task -> task.getProject().getId() == projectId && task.getUser().getUserName().equals(user.getUserName()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Task> getTasksByProjectTitle(String projectTitle) {
		return this.getAllTasks().parallelStream().filter(task -> task.getProject().getName().equalsIgnoreCase(projectTitle))
				.collect(Collectors.toList());
	}

	@Override
	public List<Task> getTasksByStatus(Status status) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		boolean isAdmin = userService.isAdmin(user);
		if (isAdmin)
			return taskRepository.findAll().parallelStream().filter(task -> task.getStatus().equalsIgnoreCase(status.getStatus()))
					.collect(Collectors.toList());
		return taskRepository.findAll().parallelStream().filter(task -> task.getStatus().equalsIgnoreCase(status.getStatus()) && task.getUser().getUserName().equals(user.getUserName()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Task> getTasksByDueDate(Date dueDate) {
		Date today = new Date();
		boolean before = today.after(dueDate);
		if (!before)
			throw new CustomException(DUE_DATE_MESSAGE);
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		boolean isAdmin = userService.isAdmin(user);
		if (isAdmin)
			return taskRepository.findAll().parallelStream().filter(task -> task.getEndDate() == dueDate)
					.collect(Collectors.toList());
		return taskRepository.findAll().parallelStream().filter(task -> task.getEndDate() == dueDate && task.getUser().getUserName().equals(user.getUserName()))
				.collect(Collectors.toList());
	}

	@Override
	public Task getTaskByTitle(String title) {
		return taskRepository.findAll().parallelStream().filter(t -> t.getTitle().equals(title)).findFirst()
				.orElse(null);
	}

	@Override
	public Task updateTask(Long id, Task task) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		boolean isAdmin = userService.isAdmin(user);
		Task findTask = taskRepository.getOne(id);
		if (findTask == null)
			throw new CustomException(TASK_MESSAGE);
		if (!findTask.getUser().getUserName().equals(user.getUserName()) && !isAdmin)
			throw new CustomException(USER_MESSAGE);
		if (findTask.getStatus().equals(CLOSED_STATUS))
			throw new CustomException(CLOSED_STATUS_EDIT_MESSAGE);
	
		@NotNull @NotEmpty String taskDesc = task.getDescription();
		@NotNull @NotEmpty String status = task.getStatus();
		@NotNull Date endDate = task.getEndDate();
		findTask.setDescription(taskDesc);
		findTask.setEndDate(endDate);
		findTask.setStatus(status);
		return taskRepository.saveAndFlush(findTask);
	}

	@Override
	public List<Task> getAllTasksByUser(String username) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		boolean isAdmin = userService.isAdmin(user);
		if (!isAdmin)
			throw new CustomException(USER_MESSAGE);
		return taskRepository.findAll().parallelStream()
				.filter(t -> t.getUser().getUserName().equals(user.getUserName())).collect(Collectors.toList());
	}
}