package com.tunehub.controller;

import com.tunehub.entity.Songs;
import com.tunehub.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongsController {

    private final SongService songService;

    @Autowired
    public SongsController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping("/create")
    public ResponseEntity<Songs> createSong(@RequestBody Songs song) {
        Songs createdSong = songService.addSong(song);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSong);
    }

    @PutMapping("/update")
    public ResponseEntity<Songs> updateSong(@RequestBody Songs song) {
        if (song.getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Songs updatedSong = songService.updateSong(song);
        if (updatedSong != null) {
            return ResponseEntity.ok(updatedSong);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteSong(@RequestBody Songs song) {
        if (song.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Songs deletedSong = songService.deleteSong(song);
        if (deletedSong != null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<Songs>> searchSongsByName(@RequestBody Songs song) {
        String name = (song != null) ? song.getName() : null;
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().body(List.of());
        }
        List<Songs> songs = songService.getSongsByName(name);
        if (!songs.isEmpty()) {
            return ResponseEntity.ok(songs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Songs>> getAllSongs() {
        List<Songs> allSongs = songService.getAllSongs();
        if (!allSongs.isEmpty()) {
            return ResponseEntity.ok(allSongs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/exists")
    public ResponseEntity<Boolean> checkIfSongUrlExists(@RequestBody Songs song) {
        String url = (song != null) ? song.getUrl() : null;
        if (url == null || url.isBlank()) {
            return ResponseEntity.badRequest().body(false);
        }
        boolean exists = songService.existsUrl(url);
        return ResponseEntity.ok(exists);
    }
}