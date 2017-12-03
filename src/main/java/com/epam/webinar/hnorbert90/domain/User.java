package com.epam.webinar.hnorbert90.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class User {
	@Id
	private Long id;
	private String username;
	private String email;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<ToDo> todo = new ArrayList<>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<ToDoArchived> ToDoArchived = new ArrayList<>();

	public User() {
	}

	public User(Long id, String username) {
		super();
		this.id = id;
		this.username = username;
	}

	public User(String username) {
		super();
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<ToDo> getTodo() {
		return todo;
	}

	public void setTodo(List<ToDo> todo) {
		this.todo = todo;
	}

	public List<ToDoArchived> getToDoArchived() {
		return ToDoArchived;
	}

	public void setToDoArchived(List<ToDoArchived> toDoArchived) {
		ToDoArchived = toDoArchived;
	}

}
