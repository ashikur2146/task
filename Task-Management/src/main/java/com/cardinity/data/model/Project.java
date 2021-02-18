package com.cardinity.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
	private Long id;
	
	@NotEmpty
	@NotNull
	@Column(unique=true)
	private String name;

	@ManyToOne
	private User user;
	
	public Project() {
		super();
	}
	
	public Project(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}
}
