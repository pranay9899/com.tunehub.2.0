package com.tunehub.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tunehub.app.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

	public Users findByEmail(String email);

}
