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

}
