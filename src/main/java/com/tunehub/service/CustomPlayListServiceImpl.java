package com.tunehub.service;

import com.tunehub.entity.CustomPlayLists;
import com.tunehub.repository.CustomPlayListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class CustomPlayListServiceImpl implements CustomPlayListService {

    private final CustomPlayListRepository customPlayListRepository;
    private final Logger logger = LoggerFactory.getLogger(CustomPlayListServiceImpl.class);
    UsersServiceImpl usersService;

    public CustomPlayListServiceImpl(CustomPlayListRepository customPlayListRepository) {
        this.customPlayListRepository = customPlayListRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<String> createCustomPlaylist(CustomPlayLists customPlayLists) {
        try {
            customPlayListRepository.save(customPlayLists);
            return ResponseEntity.status(HttpStatus.CREATED).body("Custom playlist created successfully!");
        } catch (Exception e) {
            logger.error("Error in creating custom playlist", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create custom playlist.");
        }
    }

    @Override
    @Transactional
    public List<CustomPlayLists> getAllCustomPlayListsByUserId() {
        try {
            Long id = usersService.getCurrentUserId();
            return customPlayListRepository.findAllByUsers_Id(id);
        } catch (Exception e) {
            logger.error("Error fetching custom playlists for user", e);
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteCustomPlayList(Long customPlayListId) {
        try {
            if (customPlayListRepository.existsById(customPlayListId)) {
                customPlayListRepository.deleteById(customPlayListId);
                logger.info("Custom playlist deleted successfully with ID: {}", customPlayListId);
                return ResponseEntity.noContent().build();
            } else {
                logger.warn("Custom playlist with ID {} not found for deletion.", customPlayListId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Custom playlist with ID " + customPlayListId + " not found."); // Include ID in message
            }
        } catch (Exception e) {
            logger.error("Error deleting custom playlist with ID {}", customPlayListId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete custom playlist."); // Corrected message for delete failure
        }
    }
    @Override
    @Transactional
    public ResponseEntity<String> updateCustomPlayList(CustomPlayLists playLists) {
        try {
            if (!customPlayListRepository.existsById(playLists.getId())) {
                logger.warn("Custom playlist with ID {} not found for update.", playLists.getId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Custom playlist with ID " + playLists.getId() + " not found for update."); // Corrected to 404 Not Found
            }
            customPlayListRepository.save(playLists);
            logger.info("Custom playlist updated successfully with ID: {}", playLists.getId());
            return ResponseEntity.ok("Custom playlist updated successfully!");
        } catch (Exception e) {
            logger.error("Error updating custom playlist with ID {}", playLists.getId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update custom playlist."); // Corrected error message for update
        }
    }

}
