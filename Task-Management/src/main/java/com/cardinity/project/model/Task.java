package com.cardinity.project.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Task implements Serializable {
	
	private static final long serialVersionUID = 3907282860240937923L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@NotEmpty
	@NotNull
	private String title;
	
	@NotEmpty
	@NotNull
	private String description;
	
	@NotNull
	private Status status;
	
	@NotNull
	private Project project;
	
	@Temporal(TemporalType.DATE)
	private Date creationDate;
	
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@PrePersist
	private void init() {
		this.creationDate = new Date();
	}
	
	public Task() {}

	public Task(@NotEmpty @NotNull String title, @NotEmpty @NotNull String description, @NotNull Status status,
			@NotNull Project project, Date endDate) {
		super();
		this.title = title;
		this.description = description;
		this.status = status;
		this.project = project;
		this.endDate = endDate;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Status getStatus() {
		return status;
	}

	public Project getProject() {
		return project;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}