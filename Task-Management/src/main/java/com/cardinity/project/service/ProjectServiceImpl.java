package com.cardinity.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardinity.data.model.Project;
import com.cardinity.data.repository.ProjectRepository;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Override
	public Project findProject(Long id) {
		return projectRepository.getOne(id);
	}
	
	@Override
	public Project createProject(Project project) {
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
