package com.tunehub.app.service;

import com.tunehub.app.entity.Admin;

public interface AdminService {

	public void addAdmin(Admin admin);

	public boolean validate(String email, String password);

}
