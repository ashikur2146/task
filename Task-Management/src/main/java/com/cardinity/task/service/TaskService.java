package com.cardinity.task.service;

import java.util.Date;
import java.util.List;

import com.cardinity.data.model.Status;
import com.cardinity.data.model.Task;

public interface TaskService {
		
	public Task getTaskById(Long id);
	public List<Task> getAllTasks();
	public List<Task> getTasksByProjectId(Long projectId);
	public List<Task> getTasksByProjectTitle(String projectTitle);
	public List<Task> getTasksByStatus(Status status);
	public List<Task> getTasksByDueDate(Date dueDate);
	public Task createTask(Task task);
	public Task updateTask(Task task);
	public void deleteTask(Long id);
}