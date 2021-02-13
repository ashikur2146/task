package com.cardinity.project.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Project implements Serializable {

	private static final long serialVersionUID = 8663454658967458099L;

	@Id
	@GeneratedValue
	private long id;
	
	@NotEmpty
	@NotNull
	private String title;
	
	public Project() {
		super();
	}
	
	public Project(String title) {
		super();
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
}
