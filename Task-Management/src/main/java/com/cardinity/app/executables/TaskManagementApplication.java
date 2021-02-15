package com.cardinity.app.executables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cardinity.data.model.Role;
import com.cardinity.role.service.RoleService;
import com.cardinity.user.service.UserService;

@EnableWebMvc
@SpringBootApplication
@ComponentScan(basePackages = {"com.*"})
@EntityScan(basePackages = {"com.*"})
@EnableJpaRepositories(basePackages = {"com.*"})
public class TaskManagementApplication implements CommandLineRunner {

	
	  @Autowired private RoleService roleService;
	  
	  @Autowired private UserService userService;
	 
	
	public static void main(String[] args) {
		SpringApplication.run(TaskManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		  roleService.createRole(new Role("ADMIN"));
		  roleService.createRole(new Role("USER"));
		  userService.createUser("ADMIN", "ADMIN");
		  userService.addRoleToUser("ADMIN", "ADMIN");
	}

}
