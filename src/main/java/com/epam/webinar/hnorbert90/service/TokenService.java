package com.epam.webinar.hnorbert90.service;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenService {
	private static ArrayList<Integer> tokens = new ArrayList<>();

	@RequestMapping(path = "/auth/requestToken", produces = "plain/text")
	public String getToken() {
		Integer token = generateToken();
		tokens.add(token);
		return token.toString();
	}

	private Integer generateToken() {
		return (int) (Math.random() * Integer.MAX_VALUE);
	}

	public static boolean exchangeToken(String token) {
		System.out.println(tokens.size() + "\t token:" + token);
		Integer integ = Integer.parseInt(token);
		return tokens.remove(integ);
	}
}
