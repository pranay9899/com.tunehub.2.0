package com.tunehub.app.service;

import java.util.List;

import com.tunehub.app.entity.Song;

public interface SongService {

	void addsong(Song song);

	List<Song> fetchAllSongs();

	void updateSong(Song song);
}
