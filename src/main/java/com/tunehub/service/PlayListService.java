package com.tunehub.service;

import com.tunehub.entity.PlayLists;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PlayListService {
    ResponseEntity<String> createPlaylist(PlayLists playLists);
    List<PlayLists> getAllPlayLists();
    ResponseEntity<String> deletePlayList(Long playListId);
    ResponseEntity<String> updatePlayList(PlayLists playLists);

}
