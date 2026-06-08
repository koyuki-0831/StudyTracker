package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "tasks")
public class TasksModel {
	
	@Column(nullable = false)
	@NotBlank
	private String userId;
	@Id
	//↓自動的に連番の値を作る
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;
	@NotBlank
	private String title;
	private String description;
	@NotBlank
	private String priority;
	@JsonProperty("due_date")
	@JsonFormat(pattern = "yyyy/MM/dd")
	private LocalDate due_date;
	private String status;
	private String visibility;
	//↓作成日時を自動入力
	@CreationTimestamp
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime created_at;
	@UpdateTimestamp
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime update_date;
	
	
	//------ gertter / setter ---------
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public LocalDateTime getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(LocalDateTime update_date) {
		this.update_date = update_date;
	}
	

}
