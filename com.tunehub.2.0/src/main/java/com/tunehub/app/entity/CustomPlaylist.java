package com.tunehub.app.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity

public class CustomPlaylist {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String name;

	@ManyToMany
	List<Song> songs;

	@ManyToOne
	Users user;

	public CustomPlaylist() {
		super();
	}

	public CustomPlaylist(int id, String name, List<Song> songs, Users users) {
		super();
		this.id = id;
		this.name = name;
		this.songs = songs;
		this.user = users;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "CustomPlaylist [id=" + id + ", name=" + name + ", songs=" + songs + "]";
	}

}
