package com.tunehub.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tunehub.app.entity.CustomPlaylist;

public interface CustomPlaylistRepository extends JpaRepository<CustomPlaylist, Integer> {

	List<CustomPlaylist> findByUserId(Integer userId);

}
