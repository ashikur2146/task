package com.cardinity.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {

	private static final long serialVersionUID = 1447281532287031113L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	@NotEmpty
	@NotNull
	private String userName;
	
	@NotEmpty
	@NotNull
	@JsonIgnore
	private String password;
	
	private boolean activated;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles")
	private List<Role> roles = new ArrayList<>();
	
	@OneToMany
	@JsonIgnore
	private List<Task> tasks = new ArrayList<>();
	
	@OneToMany
	@JsonIgnore
	private List<Project> projects = new ArrayList<>();
	
	public User() {
		super();
	}

	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public boolean isActivated() {
		return activated;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public String getPassword() {
		return password;
	}
	
	public List<Task> getTasks() {
		return tasks;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
