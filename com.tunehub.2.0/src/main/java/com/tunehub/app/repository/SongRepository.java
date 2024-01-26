package com.tunehub.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tunehub.app.entity.Song;

public interface SongRepository extends JpaRepository<Song, Integer> {

}
