package com.tunehub.service;

import com.tunehub.entity.PlayLists;
import com.tunehub.repository.PlayListRepository;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;


import java.util.List;

@Service
public class PlayLIstServiceImpl implements PlayListService{
    private final PlayListRepository playListRepository;
    private static final Logger logger = LoggerFactory.getLogger(PlayLIstServiceImpl.class);

    public PlayLIstServiceImpl(PlayListRepository playListRepository) {
        this.playListRepository = playListRepository;
    }

    @Override
    @Transactional
    public String addPlaylist(PlayLists playLists) {
        try {
            playListRepository.save(playLists);
            logger.info("Playlist saved successfully with ID: {}", playLists.getId());
            return "Playlist added successfully!";
        } catch (Exception e) {
            logger.error("Error saving playlist", e);
            return "Failed to add playlist.";
        }
    }

    @Override
    public List<PlayLists> getAllPlayLists() {
        try {
            List<PlayLists> playLists = playListRepository.findAll();
            if (playLists.isEmpty()) {
                logger.info("No playlists found in database.");
                return List.of();
            }
            return playLists;
        } catch (Exception e) {
            logger.error("Error retrieving all playlists", e);
            return List.of();
        }
    }

    @Override
    @Transactional
    public String deletePlayList(Long playListId) {
        try {
            if (!playListRepository.existsById(playListId)) {
                logger.warn("Attempted to delete non-existent playlist with ID: {}", playListId);
                return "Playlist not found for deletion.";
            }
            playListRepository.deleteById(playListId);
            logger.info("Playlist deleted successfully with ID: {}", playListId);
            return "Playlist deleted successfully!";
        } catch (Exception e) {
            logger.error("Error deleting playlist with ID: {}", playListId, e);
            return "Failed to delete playlist.";
        }
    }

    @Override
    @Transactional
    public String updatePlayList(PlayLists playLists) {
        try {
            if (!playListRepository.existsById(playLists.getId())) {
                logger.warn("Attempted to update non-existent playlist with ID: {}", playLists.getId());
                return "Playlist not found for update.";
            }
            playListRepository.save(playLists);
            logger.info("Playlist updated successfully with ID: {}", playLists.getId());
            return "Playlist updated successfully!";
        } catch (Exception e) {
            logger.error("Error updating playlist with ID: {}", playLists.getId(), e);
            return "Failed to update playlist.";
        }
    }
}