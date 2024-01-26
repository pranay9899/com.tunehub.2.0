package com.tunehub.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tunehub.app.entity.Admin;
import com.tunehub.app.repository.AdminRepository;

@Service
public class AdminServiceImplementation implements AdminService {

	@Autowired
	AdminRepository repo;

	@Override
	public void addAdmin(Admin admin) {
		repo.save(admin);
	}

	@Override
	public boolean validate(String email, String password) {
		Admin admin = repo.findByEmail(email);
		
		if (admin != null && admin.getPassword().equals(password)) {
			return true;
		}
		System.out.println(admin);
		return false;
	}

}
