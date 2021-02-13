package com.cardinity.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardinity.project.dao.TaskRepository;
import com.cardinity.project.model.Task;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepository taskRepository;

	@Override
	public Task getTaskById(Long id) {
		return taskRepository.getOne(id);
	}

	@Override
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	@Override
	public Task saveTask(Task task) {
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
}
