package com.tunehub.service;

import com.tunehub.entity.CustomPlayLists;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomPlayListService {
    ResponseEntity<String> createCustomPlaylist(CustomPlayLists customPlayLists);
    List<CustomPlayLists> getAllCustomPlayListsByUserId();
    ResponseEntity<String> deleteCustomPlayList(Long customPlayListId);
    ResponseEntity<String> updateCustomPlayList(CustomPlayLists playLists);
}
