package com.tunehub.service;

import com.tunehub.entity.PlayLists;

import java.util.List;

public interface PlayListService {
    String addPlaylist(PlayLists playLists);
    List<PlayLists> getAllPlayLists();
    String deletePlayList(Long playListId);
    String updatePlayList(PlayLists playLists);

}
