package com.example.demo.controller;


import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TasksRequestDto;
import com.example.demo.dto.TasksResponseDto;
import com.example.demo.service.TasksService;

@RestController
@RequestMapping("/tasks")
public class TasksController {
	
	private final TasksService tasksService;
	
	public TasksController(TasksService tasksService) {
		this.tasksService = tasksService;
	}
	
	//一覧取得
	@GetMapping
	public ResponseEntity<Page<TasksResponseDto>> getTasks(
			@RequestParam String userId,
			@RequestParam (defaultValue = "ALL") String mode,
			@RequestParam (required = false) String keyword,
			@RequestParam (required = false) String priority,
			@RequestParam (required = false) String status,
			@RequestParam (defaultValue = "0") int page,
			@RequestParam (defaultValue = "10") int size,
			@RequestParam (defaultValue = "created_at") String sortBy,
			@RequestParam (defaultValue = "desc") String direction
	) {
		Page<TasksResponseDto> result = tasksService.getTasks(
				userId,
				mode,
				keyword,
				priority,
				status,
				page,
				size,
				sortBy,
				direction
		);
		
		return ResponseEntity.ok(result);
	}
	
	//タスク新規作成
	@PostMapping("/create/{userId}")
	public ResponseEntity<TasksResponseDto> createTasks(
			@PathVariable String userId,
			@Valid @RequestBody TasksRequestDto dto
	) {
		
		TasksResponseDto created = tasksService.createTasks(userId, dto);
		
		return ResponseEntity.ok(created);
	}
	
	
	//タスク更新
	@PutMapping("/{userId}/{id}")
	public ResponseEntity<TasksResponseDto> updateTasks(
			@PathVariable Long id,
			@RequestParam String userId,
			@Valid @RequestBody TasksRequestDto dto
	) {
		
		TasksResponseDto updated = tasksService.updateTasks(id, userId, dto);
		
		return ResponseEntity.ok(updated);
	}
	
	//タスク削除
	@DeleteMapping("/{userId}/{id}")
	public ResponseEntity<Void> deleteTasks(
			@PathVariable Long id,
			@RequestParam String userId
	) {
		
		tasksService.deleteTasks(id, userId);
		
		return ResponseEntity.noContent().build();
	}

//タスク検索を一覧表示と統合したためコメントアウト	
	//タスク検索
//	@GetMapping("/tasks/search")
//	public List<TasksModel> searchTasks(
//			@RequestParam String userId,
//			@RequestParam (required = false) String keyword,
//			@RequestParam (required = false) String priority,
//			@RequestParam (defaultValue = "ALL") String mode) {
//		
//		return tasksService.searchTasks(userId, keyword, priority, mode);
//	}
	
}
