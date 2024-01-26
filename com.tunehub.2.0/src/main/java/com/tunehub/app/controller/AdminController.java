package com.tunehub.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tunehub.app.entity.Admin;
import com.tunehub.app.entity.CustomPlaylist;
import com.tunehub.app.entity.DefaultPlaylist;
import com.tunehub.app.entity.Song;
import com.tunehub.app.service.AdminService;
import com.tunehub.app.service.CustomPlaylistService;
import com.tunehub.app.service.PlaylistService;
import com.tunehub.app.service.SongService;
import com.tunehub.app.service.UserService;

import jakarta.servlet.http.HttpSession;

@CrossOrigin("*")
@Controller
public class AdminController {

	@Autowired
	SongService songservice;

	@Autowired
	PlaylistService playlistservice;

	@Autowired
	AdminService adminservice;

	@Autowired
	UserService userservice;

	@Autowired
	CustomPlaylistService customplaylistservice;

	@GetMapping("/admin")
	public String adminhome(HttpSession session) {
		if (!Validator(session)) {
			return "redirect:/login";
		}
		return "admin";
	}

	@PostMapping("/adminsignup")
	public String adminsignup(@ModelAttribute Admin admin, HttpSession session) {
		if (!StringUtils.hasText(admin.getEmail()) || !StringUtils.hasText(admin.getAddress())
				|| !StringUtils.hasText(admin.getGender()) || !StringUtils.hasText(admin.getName())) {
			return "redirect:/signup";
		}
		adminservice.addAdmin(admin);
		session.setAttribute("email", admin.getEmail());
		session.setAttribute("role", "admin");
		return "redirect:/admin";
	}

	@PostMapping("/adminlogin")
	public String adminlogin(@RequestParam String email, @RequestParam String password, HttpSession session) {
		if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) {
			return "redirect:/login";
		}
		if (adminservice.validate(email, password)) {
			session.setAttribute("email", email);
			session.setAttribute("role", "admin");
			return "redirect:/admin";
		}
		return "redirect:/login";
	}

	@PostMapping("addsong")
	public String addSong(@ModelAttribute Song song, HttpSession session) {
		if (!Validator(session)) {
			return "redirect:/login";
		}
		songservice.addsong(song);
		return "redirect:/admin";
	}

	@GetMapping("/newsong")
	public String newsong(HttpSession session) {
		if (!Validator(session)) {
			return "redirect:/login";
		}
		return "addsong";
	}

	@GetMapping("viewsongs")
	public String displaySong(Model model, HttpSession session) {

		List<Song> allSongs = songservice.fetchAllSongs();
		model.addAttribute("songs", allSongs);
		String role = (String) session.getAttribute("role");
		if ("user".equals(role)) {
			if (!userservice.membership((String) session.getAttribute("email"))) {
				return "redirect:/payment";
			} else {
				return "displaysongs";
			}
		}

		if (!Validator(session)) {
			return "redirect:/login";
		}

		return "displaysongs";
	}

	// in progress not used as of now
	@GetMapping("editsongs")
	public String EditSong(Model model) {

		List<Song> allSongs = songservice.fetchAllSongs();
		model.addAttribute("songs", allSongs);
		return "displaysongs";
	}

	@GetMapping("addplaylist")
	public String addPlaylist(Model model, HttpSession session) {
		if (!Validator(session)) {
			return "redirect:/login";
		}
		List<Song> allSongs = songservice.fetchAllSongs();
		model.addAttribute("songs", allSongs);
		return "addplaylist";
	}

	@PostMapping("/createplaylist")
	public String createPlaylist(@ModelAttribute DefaultPlaylist defaultPlaylist, HttpSession session) {
		if (!Validator(session)) {
			return "redirect:/login";
		}
		playlistservice.addPlaylist(defaultPlaylist);
		List<Song> listOfSongs = defaultPlaylist.getSongs();
		for (Song song : listOfSongs) {
			song.getDefaultPlaylist().add(defaultPlaylist);
			songservice.updateSong(song);
		}
		return "redirect:/admin";
	}

	@GetMapping("viewplaylist")
	public String displayPlaylist(Model model, HttpSession session) {
		List<DefaultPlaylist> defaultPlaylists = playlistservice.fetchAllPlaylist();
		model.addAttribute("playlists", defaultPlaylists);
		String role = (String) session.getAttribute("role");
		if ("user".equals(role)) {
			if (!userservice.membership((String) session.getAttribute("email"))) {
				return "redirect:/payment";
			} else {
				return "displayplaylist";
			}
		}
		if (!Validator(session)) {
			return "redirect:/login";
		}
		return "displayplaylist";
	}

	@GetMapping("/displayallcustomplaylist")
	public String viewAllPlaylist(Model model, HttpSession session) {

		List<CustomPlaylist> playlists = customplaylistservice.fetchAllPlaylists();
		model.addAttribute("playlists", playlists);
		if (!Validator(session)) {
			return "redirect:/payment";
		}
		return "displayallcustomplaylist";
	}

	public boolean Validator(HttpSession session) {

		String email = (String) session.getAttribute("email");
		String role = (String) session.getAttribute("role");

		return (email != null && role != null && "admin".equals(role));
	}
}
