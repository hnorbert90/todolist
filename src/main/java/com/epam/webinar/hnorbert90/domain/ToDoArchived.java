package com.epam.webinar.hnorbert90.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

@Entity
public class ToDoArchived {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String description;
	private int priority;
	private Date created;
	private Date deadline;
	private boolean done = false;
	private String details;

	@ManyToOne
	@NotNull
	private User user;

	@PrePersist
	protected void onCreate() {
		created = new Date();
	}

	public ToDoArchived() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ToDoArchived(String description, int priority) {
		super();
		this.description = description;
		this.priority = priority;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean state) {
		this.done = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ToDoArchived [id=" + id + ", description=" + description + ", priority=" + priority + ", created="
				+ created + ", deadline=" + deadline + ", done=" + done + ", details=" + details + ", user=" + user
				+ "]";
	}

}
