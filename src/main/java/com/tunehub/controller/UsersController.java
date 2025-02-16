package com.tunehub.controller;

import com.tunehub.entity.CustomPlayLists;
import com.tunehub.entity.PlayLists;
import com.tunehub.service.CustomPlayListService;
import com.tunehub.service.PlayListService;
import com.tunehub.service.UsersServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UsersController {
    CustomPlayListService customPlayListService;
    PlayListService playListService;
    UsersServiceImpl usersService;

    public UsersController(CustomPlayListService customPlayListService, PlayListService playListService, UsersServiceImpl usersService) {
        this.customPlayListService = customPlayListService;
        this.playListService = playListService;
        this.usersService = usersService;
    }

    @PostMapping("/create-playlist")
    public ResponseEntity<String> createCustomPlayList(@RequestBody CustomPlayLists customPlayList) {
        return customPlayListService.createCustomPlaylist(customPlayList);
    }

    @PutMapping("/update-playlist")
    public ResponseEntity<String> updateCustomPlayList(@RequestBody CustomPlayLists customPlayList) {
        return customPlayListService.updateCustomPlayList(customPlayList);
    }

    @DeleteMapping("/delete-playlist")
    public ResponseEntity<String> deleteCustomPlayList(@RequestBody CustomPlayLists customPlayList) {
        return customPlayListService.deleteCustomPlayList(customPlayList.getId());
    }

    @GetMapping("/get-all-custom-playlists")
    public ResponseEntity<List<CustomPlayLists>> getAllCustomPlayLists() {
        List<CustomPlayLists> playLists = customPlayListService.getAllCustomPlayListsByUserId();
        if (playLists.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(playLists);
    }

    @GetMapping("/get-all-default-playlists")
    public ResponseEntity<List<PlayLists>> getAllDefaultPlayLists() {
        List<PlayLists> playLists = playListService.getAllPlayLists();
        if (playLists.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(playLists);
    }
}