package com.cardinity.project.service;

import java.util.List;

import com.cardinity.data.model.Project;

public interface ProjectService {
	public Project findProject(Long id);
	public Project createProject(Project project);
	public Project updateProject(Project project);
	public List<Project> getAllProjects();
	public void deleteProject(Long id);
}