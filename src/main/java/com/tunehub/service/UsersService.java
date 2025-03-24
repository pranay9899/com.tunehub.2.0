package com.tunehub.service;

import com.tunehub.entity.Users;
import org.springframework.http.ResponseEntity;

public interface UsersService {

    ResponseEntity<?> validateUser(Users users); // response
    ResponseEntity<String> addUser(Users user);
    ResponseEntity<String> deleteUser(Users user);
    ResponseEntity<String> updateUser(Users user);
}
