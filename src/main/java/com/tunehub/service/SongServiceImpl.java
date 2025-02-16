package com.tunehub.service;

import com.tunehub.entity.Songs;
import com.tunehub.repository.SongRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService{
    private final SongRepository songRepository;
    private static final Logger logger = LoggerFactory.getLogger(SongServiceImpl.class);

    @Autowired
    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    @Transactional
    public Songs addSong(Songs song) {
        try {
            Songs savedSong = songRepository.save(song);
            logger.info("Song saved successfully with ID: {}", savedSong.getId());
            return savedSong;
        } catch (Exception e) {
            logger.error("Error saving song", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add song.", e);
        }
    }

    @Override
    @Transactional
    public Songs updateSong(Songs song) {
        try {
            if (!songRepository.existsById(song.getId())) {
                logger.warn("Song not found for update with ID: {}", song.getId());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found for update.");
            }
            Songs updatedSong = songRepository.save(song);
            logger.info("Song updated successfully with ID: {}", updatedSong.getId());
            return updatedSong;
        } catch (ResponseStatusException e) {
            throw e;
        }
        catch (Exception e) {
            logger.error("Error updating song with ID: {}", song.getId(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update song.", e);
        }
    }

    @Override
    @Transactional
    public Songs deleteSong(Songs song) {
        try {
            Optional<Songs> songToDeleteOptional = songRepository.findById(song.getId());
            if (songToDeleteOptional.isEmpty()) {
                logger.warn("Song not found for deletion with ID: {}", song.getId());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found for deletion.");
            }
            Songs songToDelete = songToDeleteOptional.get();
            songRepository.delete(songToDelete);
            logger.info("Song deleted successfully with ID: {}", song.getId());
            return songToDelete;
        } catch (ResponseStatusException e) {
            throw e;
        }
        catch (Exception e) {
            logger.error("Error deleting song with ID: {}", song.getId(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete song.", e);
        }
    }

    @Override
    public List<Songs> getAllSongs() {
        try {
            List<Songs> songs = songRepository.findAll();
            if (songs.isEmpty()) {
                logger.info("No songs found in database.");
                return List.of();
            }
            return songs;
        } catch (Exception e) {
            logger.error("Error retrieving all songs", e);
            return List.of();
        }
    }

    @Override
    public boolean existsUrl(String url) {
        try {
            return songRepository.findByUrl(url);
        } catch (Exception e) {
            logger.error("Error checking if URL exists: {}", url, e);
            return false;
        }
    }

    @Override
    public List<Songs> getSongsByName(String name) {
        try{
            List<Songs> songs = songRepository.findByName(name);
            if (songs.isEmpty()) {
                logger.info("No songs found in database.");
                return List.of();
            }
            return songs;
        }catch (Exception e){
            logger.error("Error getting songs by name {}", name, e);
            return List.of();
        }
    }
}