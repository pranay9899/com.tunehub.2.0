package com.tunehub.service;

import com.tunehub.entity.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsersService {
    public Users getUserById(Long id);
    public Users getUserByName(String name);
    public Users getUserByEmail(String email);

    public List<Users> getAllUsers();
    public ResponseEntity<String> saveUser(Users user);
    public ResponseEntity<String> deleteUser(Long userId);
    public ResponseEntity<Users> updateUser(Users user);
}
