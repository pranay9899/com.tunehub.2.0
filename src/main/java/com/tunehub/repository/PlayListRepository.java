package com.tunehub.repository;

import com.tunehub.entity.PlayLists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayListRepository extends JpaRepository<PlayLists, Long> {
}
