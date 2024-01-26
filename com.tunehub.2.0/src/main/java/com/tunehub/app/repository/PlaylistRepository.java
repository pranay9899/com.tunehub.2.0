package com.tunehub.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tunehub.app.entity.DefaultPlaylist;

public interface PlaylistRepository extends JpaRepository<DefaultPlaylist, Integer>{

}
