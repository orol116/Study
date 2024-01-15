package com.rest.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.entity.Users;

public interface UserJpaRepository extends JpaRepository<Users, Integer> {
	
	Optional<Users> findByUid(String email);
	
}
