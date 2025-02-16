package com.tunehub.service;

import com.tunehub.configuration.UsersPrincipal;
import com.tunehub.entity.CustomPlayLists;
import com.tunehub.repository.CustomPlayListRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    // Inject the repository via constructor
    public CustomPlayListServiceImpl(CustomPlayListRepository customPlayListRepository) {
        this.customPlayListRepository = customPlayListRepository;
    }

    @Override
    @Transactional
    public String addCustomPlaylist(CustomPlayLists customPlayLists) {
        try {
            customPlayListRepository.save(customPlayLists);
            return "Custom playlist added successfully.";
        } catch (Exception e) {
            logger.error("Error adding custom playlist", e);
            return "Failed to add custom playlist.";
        }
    }

    @Override
    @Transactional
    public List<CustomPlayLists> getAllCustomPlayListsByUserId() {
        try {
            String email = getCurrentUserEmail();
            return customPlayListRepository.findAllByEmail(email);
        } catch (Exception e) {
            logger.error("Error fetching custom playlists for user", e);
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public String deleteCustomPlayList(Long customPlayListId) {
        try {
            if (customPlayListRepository.existsById(customPlayListId)) {
                customPlayListRepository.deleteById(customPlayListId);
                return "Custom playlist deleted successfully.";
            } else {
                logger.warn("Custom playlist with ID {} not found.", customPlayListId);
                return "Custom playlist not found.";
            }
        } catch (Exception e) {
            logger.error("Error deleting custom playlist with ID {}", customPlayListId, e);
            return "Failed to delete custom playlist.";
        }
    }

    @Override
    @Transactional
    public String updateCustomPlayList(CustomPlayLists playLists) {
        try {
            if (!customPlayListRepository.existsById(playLists.getId())) {
                logger.warn("Custom playlist with ID {} not found.", playLists.getId());
                return "Custom playlist not found.";
            }
            customPlayListRepository.save(playLists);
            return "Custom playlist updated successfully.";
        } catch (Exception e) {
            logger.error("Error updating custom playlist with ID {}", playLists.getId(), e);
            return "Failed to update custom playlist.";
        }
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UsersPrincipal) {
                return ((UsersPrincipal) principal).getUsername();
            }
        }
        return null;
    }
}
