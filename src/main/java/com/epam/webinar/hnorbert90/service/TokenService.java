package com.epam.webinar.hnorbert90.service;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This service is the second security layer after google authentication
 */
@RestController
public class TokenService {
	private static ArrayList<Integer> tokens = new ArrayList<>();

	/**
	 * Generates a random number and add into tokens list.
	 */
	@RequestMapping(path = "/auth/requestToken", produces = "plain/text")
	public String getToken() {
		Integer token = generateToken();
		tokens.add(token);
		return token.toString();
	}

	private Integer generateToken() {
		return (int) (Math.random() * Integer.MAX_VALUE);
	}

	/**
	 * Checks the given token, and if this on the tokens list then remove, and
	 * return with true
	 */
	public static boolean exchangeToken(String token) {
		Integer integ = Integer.parseInt(token);
		return tokens.remove(integ);
	}
}
