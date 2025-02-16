package com.tunehub.repository;

import com.tunehub.entity.CustomPlayLists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomPlayListRepository extends JpaRepository<CustomPlayLists, Long> {
    List<CustomPlayLists> findAllByEmail(String email);
    List<CustomPlayLists> findAllByCustomPLayListNameAndEmail(String customPLayListName, String email);
}