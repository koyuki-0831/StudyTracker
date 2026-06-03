package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/tasks-page")
	public String tasks() {
		return "tasks";
	}
	
	@GetMapping("/studylogs-page")
	public String studylogs() {
		return "studylogs";
	}
	
	@GetMapping("/tasks-list")
	public String tasksList() {
		return "tasks-list";
	}
	
	@GetMapping("/tasks-regist-form")
	public String tasksRegistForm() {
		return "tasks-regist-form";
	}
	
	@GetMapping("/tasks-regist-confirm")
	public String tasksRegistConfirm() {
		return "tasks-regist-confirm";
	}
	
	@GetMapping("/tasks-search")
	public String tasksSearch() {
		return "tasks-search";
	}
	
	@GetMapping("/tasks-search-result")
	public String tasksSearchResult() {
		return "tasks-search-result";
	}
	
	@GetMapping("/tasks-update-form")
	public String tasksUpdateForm() {
		return "tasks-update-form";
	}
	
	@GetMapping("/tasks-update-confirm-page")
	public String tasksUpdateConfirmPage() {
		return "tasks-update-confirm";
	}
	
	@GetMapping("/tasks-delete-confirm")
	public String tasksDeleteConfirm() {
		return "tasks-delete-confirm";
	}
	
	@GetMapping("/studylogs-search")
	public String studyLogsSearch() {
		return "studylogs-search";
	}
	
	@GetMapping("/studylogs-search-result")
	public String studyLogsSearchResult() {
		return "studylogs-search-result";
	}
	
	@GetMapping("/studylogs-regist-form")
	public String studylogsRegistForm() {
		return "studylogs-regist-form";
	}
	
	@GetMapping("/studylogs-regist-confirm")
	public String studylogsRegistConfirm() {
		return "studylogs-regist-confirm";
	}
	
	@GetMapping("/studylogs-update-form")
	public String studylogsUpdateForm() {
		return "studylogs-update-form";
	}
	
	@GetMapping("/studylogs-update-confirm")
	public String studylogsUpdateConfirm() {
		return "studylogs-update-confirm";
	}
	
	@GetMapping("/studylogs-delete-confirm")
	public String studylogsDeleteConfirm() {
		return "studylogs-delete-confirm";
	}
	
	@GetMapping("/studylogs-list")
	public String studylogsList() {
		return "studylogs-list";
	}
	
	@GetMapping("/studylogs-detail")
	public String studylogsDetail() {
		return "studylogs-detail";
	}
	
	@GetMapping("/tasks-detail")
	public String tasksDetail() {
		return "tasks-detail";
	}

}
