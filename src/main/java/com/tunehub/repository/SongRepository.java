package com.tunehub.repository;

import com.tunehub.entity.Songs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Songs, Long> {
    boolean findByUrl(String url);
    List<Songs> findByName(String name);
}
