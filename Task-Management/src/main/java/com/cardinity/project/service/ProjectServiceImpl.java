package com.cardinity.project.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardinity.data.model.Project;
import com.cardinity.data.model.User;
import com.cardinity.data.repository.ProjectRepository;
import com.cardinity.project.exception.CustomException;
import com.cardinity.user.service.UserService;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private UserService userService;
	
	private static final String AUTHENTICATION_MESSAGE = "USER AUTHENTICATION IS REQUIRED!";
	private static final String PROJECT_MESSAGE = "Project does not exist.";
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
		project.setUser(user);
		return projectRepository.saveAndFlush(project);
	}
	
	@Override
	public Project updateProject(Project project) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		boolean isAdmin = user.getRoles().parallelStream().anyMatch(role -> role.getRoleName().equals(ADMIN_ROLE));
		Project foundProject = findProjectByTitle(project.getTitle());
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
		boolean isAdmin = user.getRoles().parallelStream().anyMatch(role -> role.getRoleName().equals(ADMIN_ROLE));
		if (!isAdmin)
			throw new CustomException(USER_MESSAGE);
		return projectRepository.findAll();
	}

	@Override
	public void deleteProject(Long id) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		boolean isAdmin = user.getRoles().parallelStream().anyMatch(role -> role.getRoleName().equals(ADMIN_ROLE));
		Project project = projectRepository.getOne(id);
		if (project == null)
			throw new CustomException(PROJECT_MESSAGE);
		if (!project.getUser().getUserName().equals(user.getUserName()) && !isAdmin)
			throw new CustomException(USER_MESSAGE);
		projectRepository.delete(project);
	}

	@Override
	public Project findProjectByTitle(String projectTitle) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		boolean isAdmin = user.getRoles().parallelStream().anyMatch(role -> role.getRoleName().equals(ADMIN_ROLE));
		Project project = this.getAllProjects().parallelStream().filter(p -> p.getTitle().equals(projectTitle))
				.findFirst().orElse(null);
		if (project == null)
			throw new CustomException(PROJECT_MESSAGE);
		if (!project.getUser().getUserName().equals(user.getUserName()) && !isAdmin)
			throw new CustomException(USER_MESSAGE);
		return project;
	}

	@Override
	public List<Project> getProjectsByUser(String username) {
		User user = userService.getAuthenticatedUser();
		if (user == null)
			throw new CustomException(AUTHENTICATION_MESSAGE);
		boolean isAdmin = user.getRoles().parallelStream().anyMatch(role -> role.getRoleName().equals(ADMIN_ROLE));
		if (!user.getUserName().equals(username) && !isAdmin)
			throw new CustomException(USER_MESSAGE);
		List<Project> projects = projectRepository.findAll().parallelStream()
				.filter(p -> p.getUser().getUserName().equals(username)).collect(Collectors.toList());
		return projects;
	}
}