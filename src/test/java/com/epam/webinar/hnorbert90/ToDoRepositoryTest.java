package com.epam.webinar.hnorbert90;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.webinar.hnorbert90.domain.ToDo;
import com.epam.webinar.hnorbert90.domain.User;
import com.epam.webinar.hnorbert90.repository.ToDoRepository;
import com.epam.webinar.hnorbert90.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest
public class ToDoRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ToDoRepository toDoRepository;

	private Pageable pageRequest;

	private User createTestUser(String email, String fullname) {
		User user = new User();
		user.setEmail(email);
		user.setUsername(fullname);
		user.setId(new Long(email.hashCode()));

		return userRepository.save(user);

	}

	@Before
	public void createTestData() {
		List<ToDo> todos = new ArrayList<>();
		todos.add(new ToDo("Test To-do", 5, new Date(), "grape federal floppy diamond footwork back",
				createTestUser("test@epam.com", "Teszt Elek")));
		todos.add(new ToDo("Test To-do2", 1, new Date(), "grape federal floppy diamond footwork back",
				createTestUser("test@epam.com", "Teszt Elek")));
		todos.add(new ToDo("Test To-do3", 10, new Date(), "grape federal floppy diamond footwork back",
				createTestUser("test@epam.com", "Teszt Elek")));

		todos.add(new ToDo("Test To-do2", 10, new Date(), "peach detachable companion distinct polite wartime",
				createTestUser("test2@epam.com", "Teszt Eltem")));
		todos.add(new ToDo("Test To-do3", 0, new Date(), "extreme beach mix prophet lion guerilla",
				createTestUser("test3@epam.com", "Teszt Al")));
		toDoRepository.save(todos);
	}

	@Test
	public void testRetrieveUser() {

		Iterable<User> users = userRepository.findAll();
		users.forEach(u -> logger.debug("User: {}", u));
		Long firstUserId = users.iterator().next().getId();
		User user = userRepository.findOne(firstUserId);
		assertThat(user).isNotNull();
		logger.debug("User: {}", user);
		logger.debug("todos: {}", user.getTodo());

		assertThat(user.getUsername()).isEqualTo("Teszt Elek");
		assertThat(user.getUsername()).startsWith("Teszt").endsWith("Elek");
	}

	@Test
	public void testRetrieveUserByUserId() {
		String email = "test@epam.com";
		User user = userRepository.findOne(new Long(email.hashCode()));
		assertThat(user.getUsername()).isEqualTo("Teszt Elek");
		assertThat(user.getUsername()).startsWith("Teszt").endsWith("Elek");
	}

	@Test
	public void testRetrieveToDoByUserId() {
		String email = "test@epam.com";
		pageRequest = new PageRequest(0, 10);
		User user = userRepository.findOne(new Long(email.hashCode()));
		Page<ToDo> todos = toDoRepository.findByUser_Id(pageRequest.first(), user.getId());
		ToDo firstTodo = (ToDo) todos.getContent().get(0);
		assertThat(firstTodo).isNotNull();
		logger.debug("Todo: {}", firstTodo);
		assertThat(firstTodo.getDescription()).isEqualTo("Test To-do");
		assertThat(firstTodo.getDetails()).isEqualTo("grape federal floppy diamond footwork back");
		assertThat(firstTodo.getPriority()).isEqualTo(5);
	}

	@Test
	public void testRetrieveUsers() {
		Iterable<User> users = userRepository.findAll();
		assertThat(users).isNotNull();
		logger.debug("Todo: {}", users);
		assertThat(users).hasSize(3);
	}

	@Test
	public void testRetrieveToDosOrderByPriorityDesc() {
		String email = "test@epam.com";
		User user = userRepository.findOne(new Long(email.hashCode()));
		pageRequest = new PageRequest(0, 10);
		Iterable<ToDo> todos = toDoRepository.findByUser_IdOrderByDoneAscPriorityDesc(pageRequest.first(),
				user.getId());
		assertThat(todos).isNotNull();
		logger.debug("Todo: {}", todos);
		assertThat(todos).hasSize(3);
		assertThat(todos.iterator().next().getPriority()).isEqualTo(10);
	}

	@Test
	public void testRetrieveToDosOrderByPriorityAsc() {
		String email = "test@epam.com";
		User user = userRepository.findOne(new Long(email.hashCode()));
		pageRequest = new PageRequest(0, 10);
		Iterable<ToDo> todos = toDoRepository.findByUser_IdOrderByPriorityAsc(pageRequest.first(), user.getId());
		assertThat(todos).isNotNull();
		logger.debug("Todo: {}", todos);
		assertThat(todos).hasSize(3);
		assertThat(todos.iterator().next().getPriority()).isEqualTo(1);
	}

	@Test
	public void testRetrieveToDosOrderByCreatedAsc() {
		String email = "test@epam.com";
		User user = userRepository.findOne(new Long(email.hashCode()));
		pageRequest = new PageRequest(0, 10);
		Iterable<ToDo> todos = toDoRepository.findByUser_IdOrderByCreatedAsc(pageRequest.first(), user.getId());
		assertThat(todos).isNotNull();
		logger.debug("Todo: {}", todos);
		assertThat(todos).hasSize(3);
		assertThat(todos.iterator().next().getPriority()).isEqualTo(5);
	}

	@Test
	public void testRetrieveToDosOrderByCreatedDesc() {
		String email = "test@epam.com";
		User user = userRepository.findOne(new Long(email.hashCode()));
		pageRequest = new PageRequest(0, 10);
		Iterable<ToDo> todos = toDoRepository.findByUser_IdOrderByCreatedDesc(pageRequest.first(), user.getId());
		assertThat(todos).isNotNull();
		logger.debug("Todo: {}", todos);
		assertThat(todos).hasSize(3);
		assertThat(todos.iterator().next().getPriority()).isEqualTo(10);
	}

}
