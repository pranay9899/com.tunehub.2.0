package com.tunehub.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tunehub.app.entity.Song;
import com.tunehub.app.repository.SongRepository;

@Service
public class SongServiceImplementation implements SongService {

	@Autowired
	SongRepository repo;

	@Override
	public void addsong(Song song) {
		repo.save(song);
	}

	@Override
	public List<Song> fetchAllSongs() {

		return repo.findAll();
	}

	@Override
	public void updateSong(Song song) {
		repo.save(song);

	}

}
