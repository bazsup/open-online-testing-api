package com.depa.user.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.depa.user.dto.UserPrincipal;
import com.depa.user.model.user.User;
import com.depa.user.repository.UserRepository;
import com.depa.user.security.exception.ResourceNotFoundException;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public User getCurrentUser(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		return (User) userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
	}
}