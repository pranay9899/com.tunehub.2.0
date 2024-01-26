package com.tunehub.app.service;

import java.util.List;

import com.tunehub.app.entity.DefaultPlaylist;

public interface PlaylistService {

	public void addPlaylist(DefaultPlaylist defaultPlaylist);

	public List<DefaultPlaylist> fetchAllPlaylist();

}
