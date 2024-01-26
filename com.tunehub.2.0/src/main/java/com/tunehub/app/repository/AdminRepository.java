package com.tunehub.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tunehub.app.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

	public Admin findByEmail(String email);

}
