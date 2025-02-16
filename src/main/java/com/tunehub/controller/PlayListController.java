package com.tunehub.controller;

import com.tunehub.entity.PlayLists;
import com.tunehub.service.PlayListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class PlayListController {
    PlayListService playlistService;

    public PlayListController(PlayListService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping("/create-playlist")
    public ResponseEntity<String> createCustomPlayList(@RequestBody PlayLists playLists) {
        return playlistService.createPlaylist(playLists);
    }

    @PutMapping("/update-playlist")
    public ResponseEntity<String> updateCustomPlayList(@RequestBody PlayLists playLists) {
        return playlistService.updatePlayList(playLists);
    }

    @DeleteMapping("/delete-playlist")
    public ResponseEntity<String> deleteCustomPlayList(@RequestBody PlayLists playLists) {
        return playlistService.deletePlayList(playLists.getId());
    }

    @GetMapping("/get-all-default-playlists")
    public ResponseEntity<List<PlayLists>> getAllDefaultPlayLists() {
        List<PlayLists> playLists = playlistService.getAllPlayLists();
        if (playLists.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(playLists);
    }
}