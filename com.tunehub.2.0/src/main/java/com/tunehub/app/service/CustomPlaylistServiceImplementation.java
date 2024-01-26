package com.tunehub.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tunehub.app.entity.CustomPlaylist;
import com.tunehub.app.repository.CustomPlaylistRepository;

@Service
public class CustomPlaylistServiceImplementation implements CustomPlaylistService {

	@Autowired
	CustomPlaylistRepository repo;

	@Override
	public void addPlaylist(CustomPlaylist playlist) {
		repo.save(playlist);
	}

	@Override
	public List<CustomPlaylist> fetchAllPlaylists() {
		return repo.findAll();
	}

	@Override
	public void updatePlaylist(CustomPlaylist customplaylist) {
		repo.save(customplaylist);

	}

	@Override
	public List<CustomPlaylist> fechAllCustomPlaylists(Integer userId) {

		return repo.findByUserId(userId);
	}

}
