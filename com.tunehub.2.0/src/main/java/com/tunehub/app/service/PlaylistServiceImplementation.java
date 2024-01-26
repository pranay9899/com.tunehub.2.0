package com.tunehub.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tunehub.app.entity.DefaultPlaylist;
import com.tunehub.app.repository.PlaylistRepository;

@Service
public class PlaylistServiceImplementation implements PlaylistService{

	@Autowired
	PlaylistRepository repo;
	
	@Override
	public void addPlaylist(DefaultPlaylist defaultPlaylist) {
		repo.save(defaultPlaylist);
	}

	@Override
	public List<DefaultPlaylist> fetchAllPlaylist() {
		return repo.findAll();
	}

}
