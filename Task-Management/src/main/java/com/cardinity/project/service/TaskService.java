package com.cardinity.project.service;

import java.util.List;

import com.cardinity.project.model.Task;

public interface TaskService {
		
	public Task getTaskById(Long id);
	public List<Task> getAllTasks();
	public Task saveTask(Task task);
	public Task updateTask(Task task);
	public void deleteTask(Long id);
}
