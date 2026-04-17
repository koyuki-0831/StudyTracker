package com.example.demo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public class TasksRequestDto {

	@NotBlank
	private  String title;

	private  String description;
	
	@NotBlank
	private String priority;

	private LocalDate due_date;

	private String visibility;
	
	//------getter / setter----------

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public LocalDate getDue_date() {
		return due_date;
	}

	public void setDue_date(LocalDate due_date) {
		this.due_date = due_date;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	
	
	

}
