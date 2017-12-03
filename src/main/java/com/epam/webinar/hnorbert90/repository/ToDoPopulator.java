package com.epam.webinar.hnorbert90.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.webinar.hnorbert90.domain.ToDo;
import com.epam.webinar.hnorbert90.domain.User;
/**
 * Pure throw-away code, just for populating the local db with test data.
 */
@Component
public class ToDoPopulator {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ToDoRepository todoRepository;
	
	private User createTestUser(String email, String fullname) {
		User user = new User();
		user.setEmail(email);
		user.setUsername(fullname);
		user.setId(new Long(email.hashCode()));
		return userRepository.save(user);
	}

	@PostConstruct
	public void createTestData() {
		List<ToDo> todos = new ArrayList<>();
		User user=createTestUser("testemail@test.com", "Teszt Elek");
		todos.add(new ToDo("Test To-do1", 5, new Date(), "grape federal floppy diamond footwork back",
				user));
		todos.add(new ToDo("Test To-do2", 1, new Date(), "grape federal floppy diamond footwork back",
				user));
		todos.add(new ToDo("Test To-do3", 10, new Date(), "grape federal floppy diamond footwork back",
				user));

		todos.add(new ToDo("Test To-do4", 3, new Date(), "peach detachable companion distinct polite wartime",
				user));
		todos.add(new ToDo("Test To-do5", 6, new Date(), "extreme beach mix prophet lion guerilla",
				user));
		todos.add(new ToDo("Test To-do6", 1, new Date(), "grape federal floppy diamond footwork back",
				user));
		todos.add(new ToDo("Test To-do7", 8, new Date(), "grape federal floppy diamond footwork back",
				user));

		todos.add(new ToDo("Test To-do8", 2, new Date(), "peach detachable companion distinct polite wartime",
				user));
		todos.add(new ToDo("Test To-do9", 4, new Date(), "extreme beach mix prophet lion guerilla",
				user));
		todoRepository.save(todos);
	}
}
