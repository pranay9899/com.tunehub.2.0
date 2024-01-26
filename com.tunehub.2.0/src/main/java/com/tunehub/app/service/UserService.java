package com.tunehub.app.service;

import com.tunehub.app.entity.Users;

public interface UserService {

	public void addUser(Users user);

	public boolean validate(String user_email, String user_password);

	public Users getUser(String email);

	public void updateUser(Users user);

	public boolean membership(String email);

}
