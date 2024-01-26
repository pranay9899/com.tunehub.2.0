package com.tunehub.app.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String name;
	String email;
	String password;
	String gender;
	String address;
	boolean isPremium;

	@OneToMany
	List<CustomPlaylist> customPlaylists;

	public Users() {
		super();
	}

	public Users(int id, String name, String email, String password, String gender, String address, boolean isPremium,
			List<CustomPlaylist> customPlaylists) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.address = address;
		this.isPremium = isPremium;
		this.customPlaylists = customPlaylists;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}

	public List<CustomPlaylist> getCustomPlaylists() {
		return customPlaylists;
	}

	public void setCustomPlaylists(List<CustomPlaylist> customPlaylists) {
		this.customPlaylists = customPlaylists;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", gender="
				+ gender + ", address=" + address + ", isPremium=" + isPremium + ", CustomPlaylists=" + customPlaylists
				+ "]";
	}

}
