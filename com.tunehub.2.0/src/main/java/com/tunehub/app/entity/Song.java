package com.tunehub.app.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Song {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String name;
	String artist;
	String genre;
	String link;

	@ManyToMany
	List<DefaultPlaylist> defaultPlaylist;

	@ManyToMany
	List<CustomPlaylist> customPlaylist;

	public Song() {
		super();
	}

	public Song(int id, String name, String artist, String genre, String link, List<DefaultPlaylist> defaultPlaylist,
			List<CustomPlaylist> userPlaylist) {
		super();
		this.id = id;
		this.name = name;
		this.artist = artist;
		this.genre = genre;
		this.link = link;
		this.defaultPlaylist = defaultPlaylist;
		this.customPlaylist = userPlaylist;
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

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<DefaultPlaylist> getDefaultPlaylist() {
		return defaultPlaylist;
	}

	public void setDefaultPlaylist(List<DefaultPlaylist> defaultPlaylist) {
		this.defaultPlaylist = defaultPlaylist;
	}

	public List<CustomPlaylist> getUserPlaylist() {
		return customPlaylist;
	}

	public void setUserPlaylist(List<CustomPlaylist> userPlaylist) {
		this.customPlaylist = userPlaylist;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", name=" + name + ", artist=" + artist + ", genre=" + genre + ", link=" + link + "]";
	}

}
