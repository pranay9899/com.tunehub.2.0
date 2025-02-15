package com.tunehub.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PlayLists {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
    String  image;
    String link;
    @ManyToMany
    List<Songs> songs;

    public PlayLists() {
    }

    public PlayLists(Long id, String name, String description, String image, String link, List<Songs> songs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.link = link;
        this.songs = songs;
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

    @Override
    public String toString() {
        return "PlayLists{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", link='" + link + '\'' +
                ", songs=" + songs +
                '}';
    }
}
