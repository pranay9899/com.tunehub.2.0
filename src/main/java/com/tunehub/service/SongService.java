package com.tunehub.service;

import com.tunehub.entity.Songs;

import java.util.List;

public interface SongService {
    Songs addSong(Songs song);
    Songs updateSong(Songs song);
    Songs deleteSong(Songs song);
    List<Songs> getAllSongs();
    boolean existsUrl(String url);
    List<Songs> getSongsByName(String name);
}
