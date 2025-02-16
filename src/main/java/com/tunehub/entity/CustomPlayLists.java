package com.tunehub.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CustomPlayLists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String customPLayListName;
    String description;
    String image;
    String link;
    @ManyToMany
    List<Songs> songs;
    @ManyToMany
    List<Users> users;

    public CustomPlayLists() {
    }

    public CustomPlayLists(Long id, String customPLayListName, String description, String image, String link, List<Songs> songs, List<Users> users) {
        this.id = id;
        this.customPLayListName = customPLayListName;
        this.description = description;
        this.image = image;
        this.link = link;
        this.songs = songs;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomPLayListName() {
        return customPLayListName;
    }

    public void setCustomPLayListName(String customPLayListName) {
        this.customPLayListName = customPLayListName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Songs> getSongs() {
        return songs;
    }

    public void setSongs(List<Songs> songs) {
        this.songs = songs;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "CustomPlayLists{" +
                "id=" + id +
                ", customPLayListName='" + customPLayListName + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", link='" + link + '\'' +
                ", songs=" + songs +
                '}';
    }
}
