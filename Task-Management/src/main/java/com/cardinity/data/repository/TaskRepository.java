package com.cardinity.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cardinity.data.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
