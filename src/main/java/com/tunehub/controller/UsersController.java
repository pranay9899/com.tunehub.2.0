package com.tunehub.controller;

import com.tunehub.entity.Users;
import com.tunehub.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
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

    @PostMapping("/delete-account")
    public ResponseEntity<String> deleteUser(@RequestBody Users user) {
        return usersService.deleteUser(user);
    }
    @PostMapping("/update-account")
    public ResponseEntity<String> updateUser(@RequestBody Users user) {
        return usersService.updateUser(user);
    }


    @GetMapping("/")
    public String home(){
        return "home";
    }



    @GetMapping("/protected-home")
    public String protectedHome(){
        return "Welcome to protected home";
    }
}