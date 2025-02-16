package com.tunehub.service;


import com.tunehub.entity.PlayLists;
import com.tunehub.repository.PlayListRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Collections;
import java.util.List;

@Service
public class PlayLIstServiceImpl implements PlayListService{

    private final PlayListRepository playListRepository;
    private final Logger logger = LoggerFactory.getLogger(PlayLIstServiceImpl.class);
    public PlayLIstServiceImpl(PlayListRepository playListRepository) {
        this.playListRepository = playListRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<String> createPlaylist(PlayLists playLists) {
        try{
            playListRepository.save(playLists);
            return ResponseEntity.status(HttpStatus.CREATED).body("Custom playlist created successfully!");
        }catch(Exception e){
            logger.error("Error in creating playlist!",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create playlist!");
        }
    }

    @Override
    @Transactional
    public List<PlayLists> getAllPlayLists() {
       try{
           return playListRepository.findAll();
       }catch (Exception e){
           logger.error("Error fetching playlist!",e);
           return Collections.emptyList();
       }
    }

    @Override
    @Transactional
    public ResponseEntity<String> deletePlayList(Long playListId) {
        try{
            if(playListRepository.existsById(playListId)){
                playListRepository.deleteById(playListId);
                logger.info("playlist deleted successfully!");
                return ResponseEntity.noContent().build();
            }else{
                logger.warn("playlist with ID {} not found for deletion.", playListId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("playlist with ID " + playListId + " not found.");
            }
        }catch(Exception e){
            logger.error("Error deleting playlist with ID {}", playListId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete playlist.");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> updatePlayList(PlayLists playLists) {
        try {
            if (!playListRepository.existsById(playLists.getId())) {
                logger.warn("playlist with ID {} not found for update.", playLists.getId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("playlist with ID " + playLists.getId() + " not found for update.");
            }
            playListRepository.save(playLists);
            logger.info("playlist updated successfully with ID: {}", playLists.getId());
            return ResponseEntity.ok("playlist updated successfully!");
        } catch (Exception e) {
            logger.error("Error updating playlist with ID {}", playLists.getId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update playlist.");
        }
    }
}