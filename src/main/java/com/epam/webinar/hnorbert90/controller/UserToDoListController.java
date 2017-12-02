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
import com.epam.webinar.hnorbert90.repository.ToDoArchivedRepository;
import com.epam.webinar.hnorbert90.repository.ToDoRepository;
import com.epam.webinar.hnorbert90.repository.UserRepository;



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
	
	@RequestMapping(value = "/{userId}/todo")
	public String displayToDoList(Model model, Pageable page,@PathVariable("userId")Long userId) {
		if (page.getPageSize() < 1 || page.getPageSize() > elementPerPage) {
			page = new PageRequest(0, elementPerPage);
		}
		model.addAttribute("todolist", fillPage(page,todoRepo.findAllByUserId(page,userId)));
		model.addAttribute("user",userRepo.findOne(userId));
		return "todo-list";
	}

	private Page<ToDo> fillPage(Pageable page,Page<ToDo> todo) {
		Page<ToDo> requestedPage = todo;		
		List<ToDo> todoList=new ArrayList<>(requestedPage.getContent());
		for(int i=todoList.size();i<elementPerPage;i++) {
			todoList.add(new ToDo());
		}
		return requestedPage= new PageImpl<ToDo>(todoList,page,requestedPage.getTotalElements());	
	}

	@RequestMapping(value = "/{userId}/todo/orderbycreated")
	public String displayToDoListOrderByCreated(Model model, Pageable page,@PathVariable("userId")Long userId) {
		if (page.getPageSize() < 1 || page.getPageSize() > elementPerPage) {
			page = new PageRequest(0, elementPerPage);
		}
		model.addAttribute("todolist", fillPage(page, todoRepo.findAllByOrderByCreated(page)));
		return "todo-list";
	}
	
	@RequestMapping(value = "/{userId}/todo/orderbycreateddesc")
	public String displayToDoListOrderByCreatedDesc(Model model, Pageable page,@PathVariable("userId")Long userId) {
		if (page.getPageSize() < 1 || page.getPageSize() > elementPerPage) {
			page = new PageRequest(0, elementPerPage);
		}
		model.addAttribute("todolist", fillPage(page, todoRepo.findAllByOrderByCreatedDesc(page)));
		return "todo-list";
	}
	
	@RequestMapping(value = "/{userId}/todo/orderbypriority")
	public String displayToDoListOrderByPriority(Model model, Pageable page,@PathVariable("userId")Long userId) {
		if (page.getPageSize() < 1 || page.getPageSize() > elementPerPage) {
			page = new PageRequest(0, elementPerPage);
		}
		model.addAttribute("todolist", fillPage(page, todoRepo.findAllByOrderByPriority(page)));
		return "todo-list";
	}
	
	@RequestMapping(value = "/{userId}/todo/orderbyprioritydesc")
	public String displayToDoListOrderByPriorityDesc(Model model, Pageable page,@PathVariable("userId")Long userId) {
		if (page.getPageSize() < 1 || page.getPageSize() > elementPerPage) {
			page = new PageRequest(0, elementPerPage);
		}
		model.addAttribute("todolist", fillPage(page, todoRepo.findAllByOrderByPriorityDesc(page)));
		return "todo-list";
	}
	
	@RequestMapping(value = "/{userId}/todo/edit/setdone/{id}")
	public String setDone(@PathVariable Long id,@PathVariable("userId")Long userId) {
		ToDo todo=todoRepo.findOne(id);
		todo.setDone(!todo.isDone());
		todoRepo.save(todo);
		return "redirect:/"+userId+"/todo";
	}
	
	@RequestMapping(value = "/{userId}/todo/new",method= RequestMethod.POST)
	public String newTodo(@RequestParam("description") String description,
			@RequestParam("details") String details,
			@RequestParam("priority") int priority,
			@RequestParam(value="deadline",
			required = false) Date deadline,
			@PathVariable("userId")Long userId) {
		ToDo todo=new ToDo(description,priority);
		todo.setUser(userRepo.findOne(userId));
		todo.setDeadline(deadline);
		todo.setDetails(details);
		todoRepo.save(todo);
		return "redirect:/"+userId+"/todo";
	}
	
	@RequestMapping(value = "/{userId}/todo/update",method= RequestMethod.POST)
	public String updateTodo(@RequestParam("description") String description,
			@RequestParam("details") String details,
			@RequestParam("priority") int priority,
			@RequestParam("id") Long id,
			@RequestParam(value="deadline" ,
			required = false) Date deadline,
			@PathVariable("userId")Long userId) {
		ToDo todo=todoRepo.findOne(id);
		todo.setDescription(description);
		todo.setPriority(priority);
		todo.setDeadline(deadline);
		todo.setDetails(details);
		todoRepo.save(todo);
		return "redirect:/"+userId+"/todo";
	}
	
	@RequestMapping("/{userId}/todo/edit/increasepriority/{id}")
	public String increasePriority(@PathVariable("id") Long id,@PathVariable("userId")Long userId) {
		ToDo todo=todoRepo.findOne(id);
		todo.setPriority(todo.getPriority()+1);
		todoRepo.save(todo);
		return "redirect:/"+userId+"/todo";
	}
	
	@RequestMapping("/{userId}/todo/edit/decreasepriority/{id}")
	public String decreasePriority(@PathVariable("id") Long id,@PathVariable("userId")Long userId) {
		ToDo todo=todoRepo.findOne(id);
		todo.setPriority(todo.getPriority()-1);
		todoRepo.save(todo);
		return "redirect:/"+userId+"/todo";
	}
	
	@RequestMapping("/{userId}/todo/delete/{id}")
	public String deleteTodo(@PathVariable("id") Long id,@PathVariable("userId")Long userId) {
		todoRepo.delete(id);
		return "redirect:/"+userId+"/todo";
	}
	
	@RequestMapping(value = "/{userId}/todo/archived")
	public String displayArchivedToDoList(Model model, Pageable page,@PathVariable("userId")Long userId) {
		if (page.getPageSize() < 1 || page.getPageSize() > elementPerPage) {
			page = new PageRequest(0, elementPerPage);
		}
		model.addAttribute("todolist", fillPageArchived(page, todoArchivedRepo.findAllByOrderByDoneAscPriorityDesc(page)));
		model.addAttribute("user",userRepo.findOne(userId));
		return "todo-list";
	}
	
	private Page<ToDoArchived> fillPageArchived(Pageable page,Page<ToDoArchived> todo) {
		Page<ToDoArchived> requestedPage = todo;		
		List<ToDoArchived> todoList=new ArrayList<>(requestedPage.getContent());
		for(int i=todoList.size();i<elementPerPage;i++) {
			todoList.add(new ToDoArchived());
		}
		return requestedPage= new PageImpl<ToDoArchived>(todoList,page,requestedPage.getTotalElements());	
	}
	
	@RequestMapping("/{userId}/todo/archive/{id}")
	public String archiveTodoById(@PathVariable("id") Long id,@PathVariable("userId")Long userId) {
		todoArchivedRepo.save(convertToArchivedToDo(todoRepo.findOne(id)));
		todoRepo.delete(id);
		return "redirect:/"+userId+"/todo";
	}
	
	@RequestMapping("/{userId}/todo/archive/done")
	public String archiveAllDoneTodo(@PathVariable("userId")Long userId) {
		List<ToDo> doneTodos=todoRepo.findAllByDone(true);
		todoArchivedRepo.save(convertToArchivedToDoList(doneTodos));
		todoRepo.delete(doneTodos);
		return "redirect:/"+userId+"/todo";
	}
	
	private List<ToDoArchived> convertToArchivedToDoList(List<ToDo> todo){
		ArrayList<ToDoArchived> archTodo=new ArrayList<>();
		for(ToDo element: todo) {
			archTodo.add(convertToArchivedToDo(element));
		}
		return archTodo;
	}
	
	private ToDoArchived convertToArchivedToDo(ToDo todo){
			ToDoArchived converted = new ToDoArchived();
			converted.setCreated(todo.getCreated());
			converted.setDeadline(todo.getDeadline());
			converted.setDescription(todo.getDescription());
			converted.setDone(todo.isDone());
			converted.setPriority(todo.getPriority());
			converted.setDetails(todo.getDetails());
		return converted;
	}
	
	@RequestMapping("/{userId}/todo/archived/restore/{id}")
	public String restoreArchiveTodoById(@PathVariable("id") Long id,@PathVariable("userId")Long userId) {
		ToDo todo = convertBackToToDo(todoArchivedRepo.findOne(id));
		todo.setUser(userRepo.findOne(userId));
		todoRepo.save(todo);
		todoArchivedRepo.delete(id);
		return "redirect:/"+userId+"/todo/archived";
	}
	
	private List<ToDo> convertBackToToDoList(List<ToDoArchived> archTodo){
		ArrayList<ToDo> todo=new ArrayList<>();
		for(ToDoArchived element: archTodo) {
			todo.add(convertBackToToDo(element));
		}
		return todo;
	}
	
	private ToDo convertBackToToDo(ToDoArchived archtodo){
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
