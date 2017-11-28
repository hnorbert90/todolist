package com.epam.webinar.hnorbert90.controller.form;

public class ToDoForm {
	private String description;
	private int priority;

	public ToDoForm() {
		super();
	}

	public ToDoForm(String description, int priority) {
		super();
		this.description = description;
		this.priority = priority;
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

}
