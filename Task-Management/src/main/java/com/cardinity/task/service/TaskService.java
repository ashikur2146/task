package com.cardinity.task.service;

import java.text.ParseException;
import java.util.List;

import com.cardinity.data.model.Status;
import com.cardinity.data.model.Task;

public interface TaskService {
		
	public Task getTaskById(Long id);
	public Task getTaskByTitle(String title);
	public List<Task> getAllTasks();
	public List<Task> getAllTasksByUser(String username);
	public List<Task> getTasksByProjectId(Long projectId);
	public List<Task> getTasksByProjectTitle(String projectTitle);
	public List<Task> getTasksByStatus(Status status);
	public List<Task> getExpiredTasks();
	public Task createTask(Task task);
	public Task updateTask(Long id, Task task);
	public void deleteTask(Long id);
}