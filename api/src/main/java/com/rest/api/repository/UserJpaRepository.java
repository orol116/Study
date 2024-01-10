package com.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.entity.Users;

public interface UserJpaRepository extends JpaRepository<Users, Long> {

}
