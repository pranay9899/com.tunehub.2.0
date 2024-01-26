package com.tunehub.app.service;

import java.util.List;

import com.tunehub.app.entity.CustomPlaylist;

public interface CustomPlaylistService {

	public void addPlaylist(CustomPlaylist playlist);

	public List<CustomPlaylist> fetchAllPlaylists();

	public void updatePlaylist(CustomPlaylist customplaylist);

	public List<CustomPlaylist> fechAllCustomPlaylists(Integer userId);

}
