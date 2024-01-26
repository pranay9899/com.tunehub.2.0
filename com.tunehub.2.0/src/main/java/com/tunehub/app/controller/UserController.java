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

import com.tunehub.app.entity.CustomPlaylist;
import com.tunehub.app.entity.Song;
import com.tunehub.app.entity.Users;
import com.tunehub.app.service.CustomPlaylistService;
import com.tunehub.app.service.SongService;
import com.tunehub.app.service.UserService;

import jakarta.servlet.http.HttpSession;

@CrossOrigin("*")
@Controller
public class UserController {

	@Autowired
	CustomPlaylistService customplaylistservice;

	@Autowired
	SongService songservice;

	@Autowired
	UserService userservice;

	@PostMapping("/usersignup")
	public String usersignup(Users user, HttpSession session) {

		if (!StringUtils.hasText(user.getEmail()) || !StringUtils.hasText(user.getAddress())) {
			return "redirect:/signup";
		}

		userservice.addUser(user);
		session.setAttribute("email", user.getEmail());
		session.setAttribute("role", "user");
		return "login";
	}

	@PostMapping("/userlogin")
	public String userlogin(@RequestParam String email, @RequestParam String password, HttpSession session) {

		if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) {
			return "redirect:/login";
		}

		if (userservice.validate(email, password)) {
			session.setAttribute("email", email);
			session.setAttribute("role", "user");
			return "redirect:/userhome";
		}
		return "redirect:/login";
	}

	@GetMapping("/userhome")
	public String userhome(HttpSession session) {
		if (!Validator(session)) {
			return "redirect:/home";
		}
		return "userhome";
	}

	@GetMapping("/displaycustomplaylist")
	public String viewPlaylist(Model model, HttpSession session) {
		if (!Validator(session)) {
			return "redirect:/payment";
		}
		String email = (String) session.getAttribute("email");
		Integer userId = userservice.getUser(email).getId();
		List<CustomPlaylist> customplaylists = customplaylistservice.fechAllCustomPlaylists(userId);
		model.addAttribute("playlists", customplaylists);

		return "displaycustomplaylist";
	}

	@GetMapping("/addcustomplaylistredirect")
	public String addcustomplaylistredirect(Model model, HttpSession session) {
		if ("admin".equals((String) session.getAttribute("role"))) {
			return "redirect:/login";
		}

		if (!Validator(session)) {
			return "redirect:/payment";
		}
		List<Song> allSongs = songservice.fetchAllSongs();
		model.addAttribute("songs", allSongs);
		return "addcustomplaylist"; // custom
	}

	@PostMapping("/createcustomplaylist")
	public String addcustomPlaylist(@ModelAttribute CustomPlaylist playlist, HttpSession session) {
		if (!Validator(session)) {
			return "redirect:/login";
		}
		Users user = userservice.getUser((String) session.getAttribute("email"));

		playlist.setUser(user);
		customplaylistservice.addPlaylist(playlist);
		user.getCustomPlaylists().add(playlist);
		userservice.updateUser(user);

		List<Song> listOfSongs = playlist.getSongs();
		for (Song song : listOfSongs) {
			song.getUserPlaylist().add(playlist);
			songservice.updateSong(song);
		}
		return "redirect:/userhome";
	}

	@GetMapping("/editcustomplaylist")
	public String editPlaylist(Model model, HttpSession session) {

		if ("admin".equals((String) session.getAttribute("role"))) {
			return "editcustomplaylist";
		}

		if (!Validator(session)) {
			return "redirect:/login";
		}
		List<CustomPlaylist> playlists = customplaylistservice.fetchAllPlaylists();
		model.addAttribute("playlists", playlists);
		return "editcustomplaylist";
	}

	public boolean Validator(HttpSession session) {

		String email = (String) session.getAttribute("email");
		String role = (String) session.getAttribute("role");
		boolean membership = userservice.membership(email);
		if ("admin".equals(role)) {
			return true;
		}

		return (email != null && role != null && "user".equals(role) && membership == true);
	}

}
