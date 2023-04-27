package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.UserRepository;
import com.example.entity.User;

@RestController
public class AdminController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/admin")
	public String index() {
		
		Iterable<User> users = userRepository.findAll();
		StringBuilder sb = new StringBuilder(1000);
		for (User user : users) {
			sb.append(user.getId() + " " + user.getFirstName() + " " + user.getLastName() + "\n");
		}
		
		return "Users:" + sb.toString();
	}
	

}
