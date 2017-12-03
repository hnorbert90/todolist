package com.epam.webinar.hnorbert90.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epam.webinar.hnorbert90.domain.User;
import com.epam.webinar.hnorbert90.repository.UserRepository;
import com.epam.webinar.hnorbert90.service.TokenService;

@Controller
public class loginController {
	@Autowired
	private UserRepository userRepo;

	@RequestMapping(value = "/")
	public String displayIndex() {
		return "index";
	}

	@RequestMapping(value = "/login/{email}/{name}/{token}")
	public String login(@PathVariable("email") String email, @PathVariable("name") String name,
			@PathVariable("token") String token) {
		if (TokenService.exchangeToken(token)) {
			Long userId = new Long(email.hashCode());
			User user = userRepo.findOne(userId);
			if (Objects.nonNull(user)) {
				user.setEmail(email);
				user.setUsername(name);
			} else {
				user = new User();
				user.setId(userId);
				user.setEmail(email);
				user.setUsername(name);
			}
			userRepo.save(user);
			return "redirect:/" + userId + "/todo?order=default";
		}
		return "index";
	}
}
