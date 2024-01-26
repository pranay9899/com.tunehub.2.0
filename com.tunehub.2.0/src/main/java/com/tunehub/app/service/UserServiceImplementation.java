package com.tunehub.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tunehub.app.entity.Users;
import com.tunehub.app.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	UserRepository repo;

	@Override
	public void addUser(Users user) {
		repo.save(user);
	}

	@Override
	public boolean validate(String email, String password) {
		if (email == null || password == null) {
			return false;
		}
		Users user = repo.findByEmail(email);
		if (user != null && user.getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	@Override
	public Users getUser(String email) {
		if (email == null) {
			return null;
		}
		return repo.findByEmail(email);
	}

	@Override
	public void updateUser(Users user) {
		repo.save(user);
	}

	@Override
	public boolean membership(String email) {
		if (email == null) {
			return false;
		}

		return repo.findByEmail(email).isPremium();
	}

}
