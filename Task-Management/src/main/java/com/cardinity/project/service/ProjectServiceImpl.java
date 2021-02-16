package com.cardinity.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	
	@Override
	public Project findProject(Long id) {
		return projectRepository.getOne(id);
	}
	
	@Override
	public Project createProject(Project project) {
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		User user = userService.getUserByName(username);
		if (user == null)
			throw new CustomException("Login before creating a project");
		project.setUser(user);
		return projectRepository.saveAndFlush(project);
	}
	
	@Override
	public Project updateProject(Project project) {
		return projectRepository.saveAndFlush(project);
	}

	@Override
	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	@Override
	public void deleteProject(Long id) {
		Project project = projectRepository.getOne(id);
		projectRepository.delete(project);
	}

	@Override
	public Project findProjectByTitle(String projectTitle) {
		Project project = this.getAllProjects().parallelStream().filter(p -> p.getTitle().equals(projectTitle))
				.findFirst().orElse(null);
		return project;
	}
}
