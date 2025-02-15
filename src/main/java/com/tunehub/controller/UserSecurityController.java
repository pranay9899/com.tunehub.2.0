package com.tunehub.controller;

import com.tunehub.entity.Users;
import com.tunehub.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserSecurityController {

    private final UsersService usersService;

    public UserSecurityController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return usersService.validateUser(user);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Users user) {
        return usersService.addUser(user);
    }

    @PostMapping("/update-account")
    public ResponseEntity<String> updateUser(@RequestBody Users user) {
        return usersService.updateUser(user);
    }

    @PostMapping("/delete-account")
    public ResponseEntity<String> deleteUser(@RequestBody Users user) {
        return usersService.deleteUser(user);
    }

    @GetMapping("/")
    public String home(){
        return "welcome to tune hub";
    }

    @GetMapping("/protected-home")
    public String protectedHome(){
        return "Welcome to protected home";
    }
}
