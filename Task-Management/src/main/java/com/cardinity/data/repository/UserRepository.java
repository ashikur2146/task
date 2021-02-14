package com.cardinity.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cardinity.data.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
}