package com.cardinity.project.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardinity.data.model.Project;
import com.cardinity.data.model.Task;
import com.cardinity.data.model.User;
import com.cardinity.data.repository.ProjectRepository;
import com.cardinity.project.exception.CustomException;
import com.cardinity.task.service.TaskService;
import com.cardinity.user.service.UserService;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TaskService taskService;
	
	private static final String AUTHENTICATION_MESSAGE = "USER AUTHENTICATION IS REQUIRED!";
	private static final String PROJECT_MESSAGE = "Project does not exist.";
	private static final String PROJECT_MESSAGE_2 = "Project already exists.";
	private static final String PROJECT_MESSAGE_3 = "Invalid Project creation request.";
	private static final String USER_MESSAGE = "User does not have access right.";
	private static final String ADMIN_ROLE = "ADMIN";
	
	@Override
	public Project findProject(Long id) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		boolean isAdmin = user.getRoles().parallelStream().anyMatch(role -> role.getRoleName().equals(ADMIN_ROLE));
		Project project = projectRepository.getOne(id);
	    if (project == null)
			throw new CustomException(PROJECT_MESSAGE);
		if (!project.getUser().getUserName().equals(user.getUserName()) && !isAdmin)
			throw new CustomException(USER_MESSAGE);
		return project;
	}
	
	@Override
	public Project createProject(Project project) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		Project projectFound = projectRepository.findAll().parallelStream().filter(p -> p.getName().equals(project.getName()))
				.findFirst().orElse(null);
		if (projectFound != null)
			throw new CustomException(PROJECT_MESSAGE_2);
		project.setUser(user);
		if (project.getName() == null)
			throw new CustomException(PROJECT_MESSAGE_3);
		return projectRepository.saveAndFlush(project);
	}
	
	@Override
	public Project updateProject(Project project) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		boolean isAdmin = user.getRoles().parallelStream().anyMatch(role -> role.getRoleName().equals(ADMIN_ROLE));
		Project foundProject = findProjectByTitle(project.getName());
	    if (foundProject == null)
			throw new CustomException(PROJECT_MESSAGE);
		if (!project.getUser().getUserName().equals(user.getUserName()) && !isAdmin)
			throw new CustomException(USER_MESSAGE);
		return projectRepository.saveAndFlush(project);
	}

	@Override
	public List<Project> getAllProjects() {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		boolean isAdmin = userService.isAdmin(user);
		if (isAdmin)
			return projectRepository.findAll();
		return projectRepository.findAll().parallelStream()
				.filter(p -> p.getUser().getUserName().equals(user.getUserName())).collect(Collectors.toList());
	}

	@Override
	public void deleteProject(Long id) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		boolean isAdmin = userService.isAdmin(user);
		Project project = projectRepository.getOne(id);
		if (project == null)
			throw new CustomException(PROJECT_MESSAGE);
		if (!project.getUser().getUserName().equals(user.getUserName()) && !isAdmin)
			throw new CustomException(USER_MESSAGE);
		List<Task> allTasksByProject = taskService.getAllTasks().parallelStream()
				.filter(t -> t.getProject().getId() == id).collect(Collectors.toList());
		allTasksByProject.forEach(t -> {
			taskService.deleteTask(t.getId());
		});
		projectRepository.delete(project);
	}

	@Override
	public Project findProjectByTitle(String projectTitle) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		boolean isAdmin = userService.isAdmin(user);
		Project project = projectRepository.findAll().parallelStream().filter(p -> p.getName().equals(projectTitle))
				.findFirst().orElse(null);
		if (project == null)
			throw new CustomException(PROJECT_MESSAGE);
		if (!project.getUser().getUserName().equals(user.getUserName()) && !isAdmin)
			throw new CustomException(USER_MESSAGE);
		return project;
	}
	 
	
	/*
	 * @Override public Project findProjectByTitle(String projectTitle) { return
	 * projectRepository.findAll().parallelStream().filter(p ->
	 * p.getName().equals(projectTitle)) .findFirst().orElse(null); }
	 */
	

	@Override
	public List<Project> getProjectsByUser(String username) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		boolean isAdmin = userService.isAdmin(user);
		if (!isAdmin)
			throw new CustomException(USER_MESSAGE);
		List<Project> projects = projectRepository.findAll().parallelStream()
				.filter(p -> p.getUser().getUserName().equals(username)).collect(Collectors.toList());
		return projects;
	}
}