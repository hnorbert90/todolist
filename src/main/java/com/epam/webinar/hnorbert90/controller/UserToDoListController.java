package com.epam.webinar.hnorbert90.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.webinar.hnorbert90.domain.ToDo;
import com.epam.webinar.hnorbert90.domain.ToDoArchived;
import com.epam.webinar.hnorbert90.domain.User;
import com.epam.webinar.hnorbert90.repository.ToDoArchivedRepository;
import com.epam.webinar.hnorbert90.repository.ToDoRepository;
import com.epam.webinar.hnorbert90.repository.UserRepository;

/**
 * This controller is responsible for displaying editing, creating, deleting,
 * and sorting user's task list.
 */
@Controller
public class UserToDoListController {
	@Autowired
	private ToDoRepository todoRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ToDoArchivedRepository todoArchivedRepo;

	@Value(value = "${todo.elementPerPage}")
	private int elementPerPage;

	/**
	 * Lists the user's tasks in ordered sequence.
	 */
	@RequestMapping(value = "/{userId}/todo")
	public String displayToDoList(Model model, Pageable page, @RequestParam(value = "order") String order,
			@PathVariable("userId") Long userId) {
		if (page.getPageSize() < 1 || page.getPageSize() > elementPerPage) {
			page = new PageRequest(0, elementPerPage);
		}
		User user = userRepo.findOne(userId);

		switch (order) {
		case "orderbycreatedasc":
			model.addAttribute("todolist", fillPage(page, todoRepo.findByUser_IdOrderByCreatedAsc(page, user.getId())));

			break;
		case "orderbycreatedddesc":
			model.addAttribute("todolist", fillPage(page, todoRepo.findByUser_IdOrderByCreatedDesc(page, userId)));

			break;
		case "orderbypriorityasc":
			model.addAttribute("todolist", fillPage(page, todoRepo.findByUser_IdOrderByPriorityAsc(page, userId)));

			break;
		case "orderbyprioritydesc":
			model.addAttribute("todolist", fillPage(page, todoRepo.findByUser_IdOrderByPriorityDesc(page, userId)));

			break;
		case "orderbydeadlineasc":
			model.addAttribute("todolist", fillPage(page, todoRepo.findByUser_IdOrderByDeadlineAsc(page, userId)));

			break;
		case "orderbydeadlinedesc":
			model.addAttribute("todolist", fillPage(page, todoRepo.findByUser_IdOrderByDeadlineDesc(page, userId)));

			break;
		default:
			model.addAttribute("todolist",
					fillPage(page, todoRepo.findByUser_IdOrderByDoneAscPriorityDesc(page, userId)));

			break;
		}
		model.addAttribute("user", user);
		return "todo-list";
	}

	/**
	 * fills the page with empty records
	 */
	private Page<ToDo> fillPage(Pageable page, Page<ToDo> todo) {
		Page<ToDo> requestedPage = todo;
		List<ToDo> todoList = new ArrayList<>(requestedPage.getContent());
		for (int i = todoList.size(); i < elementPerPage; i++) {
			todoList.add(new ToDo());
		}
		return requestedPage = new PageImpl<ToDo>(todoList, page, requestedPage.getTotalElements());
	}

	/**
	 * Marks as completed task
	 */
	@RequestMapping(value = "/{userId}/todo/edit/setdone/{id}")
	public String setDone(@PathVariable Long id, @PathVariable("userId") Long userId) {
		ToDo todo = todoRepo.findOne(id);
		todo.setDone(!todo.isDone());
		todoRepo.save(todo);
		return "redirect:/" + userId + "/todo?order=default";
	}

	/**
	 * Created new task.
	 */
	@RequestMapping(value = "/{userId}/todo/new", method = RequestMethod.POST)
	public String newTodo(@RequestParam("description") String description, @RequestParam("details") String details,
			@RequestParam("priority") int priority, @RequestParam(value = "deadline", required = false) Date deadline,
			@PathVariable("userId") Long userId) {
		ToDo todo = new ToDo(description, priority);
		todo.setUser(userRepo.findOne(userId));
		todo.setDeadline(deadline);
		todo.setDetails(details);
		todoRepo.save(todo);
		return "redirect:/" + userId + "/todo?order=default";
	}

	/**
	 * Updates a task.
	 */
	@RequestMapping(value = "/{userId}/todo/update", method = RequestMethod.POST)
	public String updateTodo(@RequestParam("description") String description, @RequestParam("details") String details,
			@RequestParam("priority") int priority, @RequestParam("id") Long id,
			@RequestParam(value = "deadline", required = false) Date deadline, @PathVariable("userId") Long userId) {
		ToDo todo = todoRepo.findOne(id);
		todo.setDescription(description);
		todo.setPriority(priority);
		todo.setDeadline(deadline);
		todo.setDetails(details);
		todoRepo.save(todo);
		return "redirect:/" + userId + "/todo?order=default";
	}

	/**
	 * Increasing a task priority
	 */
	@RequestMapping("/{userId}/todo/edit/increasepriority/{id}")
	public String increasePriority(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
		ToDo todo = todoRepo.findOne(id);
		todo.setPriority(todo.getPriority() + 1);
		todoRepo.save(todo);
		return "redirect:/" + userId + "/todo?order=default";
	}

	/**
	 * Decreasing a task priority
	 */
	@RequestMapping("/{userId}/todo/edit/decreasepriority/{id}")
	public String decreasePriority(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
		ToDo todo = todoRepo.findOne(id);
		todo.setPriority(todo.getPriority() - 1);
		todoRepo.save(todo);
		return "redirect:/" + userId + "/todo?order=default";
	}

	/**
	 * Delete a task by id.
	 */
	@RequestMapping("/{userId}/todo/delete/{id}")
	public String deleteTodo(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
		todoRepo.delete(id);
		return "redirect:/" + userId + "/todo?order=default";
	}

	/**
	 * Lists the archived tasks in ordered sequence.
	 */
	@RequestMapping(value = "/{userId}/todo/archived")
	public String displayToDoArchivedListOrderByCreated(Model model, Pageable page,
			@RequestParam(value = "order") String order, @PathVariable("userId") Long userId) {
		if (page.getPageSize() < 1 || page.getPageSize() > elementPerPage) {
			page = new PageRequest(0, elementPerPage);
		}
		User user = userRepo.findOne(userId);

		switch (order) {
		case "orderbycreatedasc":
			model.addAttribute("todolist",
					fillPageArchived(page, todoArchivedRepo.findByUser_IdOrderByCreatedAsc(page, user.getId())));

			break;
		case "orderbycreatedddesc":
			model.addAttribute("todolist",
					fillPageArchived(page, todoArchivedRepo.findByUser_IdOrderByCreatedDesc(page, userId)));

			break;
		case "orderbypriorityasc":
			model.addAttribute("todolist",
					fillPageArchived(page, todoArchivedRepo.findByUser_IdOrderByPriorityAsc(page, userId)));

			break;
		case "orderbyprioritydesc":
			model.addAttribute("todolist",
					fillPageArchived(page, todoArchivedRepo.findByUser_IdOrderByPriorityDesc(page, userId)));

			break;
		case "orderbydeadlineasc":
			model.addAttribute("todolist",
					fillPageArchived(page, todoArchivedRepo.findByUser_IdOrderByDeadlineAsc(page, userId)));

			break;
		case "orderbydeadlinedesc":
			model.addAttribute("todolist",
					fillPageArchived(page, todoArchivedRepo.findByUser_IdOrderByDeadlineDesc(page, userId)));

			break;
		default:
			model.addAttribute("todolist",
					fillPageArchived(page, todoArchivedRepo.findByUser_IdOrderByDoneAscPriorityDesc(page, userId)));

			break;
		}
		model.addAttribute("user", user);
		return "todo-list";
	}

	/**
	 * fills the page with empty records
	 */
	private Page<ToDoArchived> fillPageArchived(Pageable page, Page<ToDoArchived> todo) {
		Page<ToDoArchived> requestedPage = todo;
		List<ToDoArchived> todoList = new ArrayList<>(requestedPage.getContent());
		for (int i = todoList.size(); i < elementPerPage; i++) {
			todoList.add(new ToDoArchived());
		}
		return requestedPage = new PageImpl<ToDoArchived>(todoList, page, requestedPage.getTotalElements());
	}

	/**
	 * Archive a task by id.
	 */
	@RequestMapping("/{userId}/todo/archive/{id}")
	public String archiveTodoById(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
		ToDoArchived todo = convertToArchivedToDo(todoRepo.findOne(id));
		todo.setUser(userRepo.findOne(userId));
		todoArchivedRepo.save(todo);
		todoRepo.delete(id);
		return "redirect:/" + userId + "/todo?order=default";
	}

	/**
	 * Archive all task which is marked as completed.
	 */
	@RequestMapping("/{userId}/todo/archive/done")
	public String archiveAllDoneTodo(@PathVariable("userId") Long userId) {
		List<ToDo> doneTodos = todoRepo.findAllByDone(true);
		User user = userRepo.findOne(userId);
		List<ToDoArchived> todo = convertToArchivedToDoList(doneTodos, user);
		todoArchivedRepo.save(todo);
		todoRepo.delete(doneTodos);
		return "redirect:/" + userId + "/todo?order=default";
	}

	/**
	 * Converts ToDo list to ToDoArchived list.
	 */
	private List<ToDoArchived> convertToArchivedToDoList(List<ToDo> todo, User user) {
		ArrayList<ToDoArchived> archTodo = new ArrayList<>();
		for (ToDo element : todo) {
			ToDoArchived todoArchived = convertToArchivedToDo(element);
			todoArchived.setUser(user);
			archTodo.add(todoArchived);
		}
		return archTodo;
	}

	/**
	 * Converts ToDoArchived to ToDo.
	 */
	private ToDoArchived convertToArchivedToDo(ToDo todo) {
		ToDoArchived converted = new ToDoArchived();
		converted.setCreated(todo.getCreated());
		converted.setDeadline(todo.getDeadline());
		converted.setDescription(todo.getDescription());
		converted.setDone(todo.isDone());
		converted.setPriority(todo.getPriority());
		converted.setDetails(todo.getDetails());
		return converted;
	}

	/**
	 * Restore an archived task from the archived list.
	 */
	@RequestMapping("/{userId}/todo/archived/restore/{id}")
	public String restoreArchiveTodoById(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
		ToDo todo = convertBackToToDo(todoArchivedRepo.findOne(id));
		todo.setUser(userRepo.findOne(userId));
		todoRepo.save(todo);
		todoArchivedRepo.delete(id);
		return "redirect:/" + userId + "/todo/archived?order=default";
	}

	/**
	 * Converts ToDoArchived list to ToDo list. //Not used yet
	 */
	private List<ToDo> convertBackToToDoList(List<ToDoArchived> archTodo) {
		ArrayList<ToDo> todo = new ArrayList<>();
		for (ToDoArchived element : archTodo) {
			todo.add(convertBackToToDo(element));
		}
		return todo;
	}

	/**
	 * Convert ToDoArchived to ToDo
	 */
	private ToDo convertBackToToDo(ToDoArchived archtodo) {
		ToDo converted = new ToDo();
		converted.setCreated(archtodo.getCreated());
		converted.setDeadline(archtodo.getDeadline());
		converted.setDescription(archtodo.getDescription());
		converted.setDone(archtodo.isDone());
		converted.setPriority(archtodo.getPriority());
		converted.setDetails(archtodo.getDetails());
		return converted;
	}

}
