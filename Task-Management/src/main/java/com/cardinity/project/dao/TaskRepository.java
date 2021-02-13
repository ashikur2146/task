package com.cardinity.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cardinity.project.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
