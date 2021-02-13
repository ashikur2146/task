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
import com.cardinity.data.repository.ProjectRepository;
import com.cardinity.data.repository.TaskRepository;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ProjectRepository projectRepository;

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
		Project project = task.getProject();
		Boolean hasProject = projectRepository.existsById(project.getId());
		if (!hasProject)
			return null;
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
