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
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name = "users")
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
	private String password;
	
	private boolean activated;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles")
	private List<Role> roles = new ArrayList<>();
	
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
}
