package com.tunehub.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PlayLists {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    String playLIstName;
    String description;
    String  image;
    String link;
    @ManyToMany
    List<Songs> songs;

    public PlayLists() {
    }

    public PlayLists(Long id, String playLIstName, String description, String image, String link, List<Songs> songs) {
        this.id = id;
        this.playLIstName = playLIstName;
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

    public String getPlayLIstName() {
        return playLIstName;
    }

    public void setPlayLIstName(String playLIstName) {
        this.playLIstName = playLIstName;
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
                ", playLIstName='" + playLIstName + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", link='" + link + '\'' +
                ", songs=" + songs +
                '}';
    }
}
