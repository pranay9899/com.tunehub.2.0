package com.tunehub.service;

import com.tunehub.entity.CustomPlayLists;

import java.util.List;

public interface CustomPlayListService {
    String addCustomPlaylist(CustomPlayLists customPlayLists);
    List<CustomPlayLists> getAllCustomPlayListsByUserId();
    String deleteCustomPlayList(Long customPlayListId);
    String updateCustomPlayList(CustomPlayLists playLists);
}
