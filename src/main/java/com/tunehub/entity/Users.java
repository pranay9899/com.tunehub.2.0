package com.tunehub.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<CustomPlayLists>  customPlayLists;
    public Users() {
    }

    public Users(Long id, String name, String email, String password, Role role, List<CustomPlayLists> customPlayLists) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.customPlayLists = customPlayLists;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<CustomPlayLists> getCustomPlayLists() {
        return customPlayLists;
    }

    public void setCustomPlayLists(List<CustomPlayLists> customPlayLists) {
        this.customPlayLists = customPlayLists;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", customPlayLists=" + customPlayLists +
                '}';
    }
}
