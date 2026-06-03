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

import com.example.demo.dto.StudyLogRequestDto;
import com.example.demo.dto.StudyLogResponseDto;
import com.example.demo.dto.StudyLogUpdateDto;
import com.example.demo.service.StudyLogService;

@RestController
@RequestMapping("/studylogs")
public class StudyLogController {
	
	private final StudyLogService studyLogService;
	
	public StudyLogController(StudyLogService studyLogService) {
		this.studyLogService = studyLogService;
	}
	
	//==============================
	// 学習記録登録
	//==============================
	@PostMapping("/tasks/{taskId}")
	public ResponseEntity<StudyLogResponseDto> create(
			@PathVariable Long taskId,
			@RequestParam String userId,
			@Valid @RequestBody StudyLogRequestDto dto
	) {
		
		StudyLogResponseDto response =
				studyLogService.create(userId, taskId, dto);
		
		return ResponseEntity.ok(response);
	}
	
	//================================
	// 学習記録更新
	//================================
	@PutMapping("/{userId}/{id}")
	public ResponseEntity<StudyLogResponseDto> update(
			@PathVariable String userId,
			@PathVariable Long id,
			@Valid @RequestBody StudyLogUpdateDto dto
	 ) {
		
		StudyLogResponseDto response =
				studyLogService.update(userId, id, dto);
		
		return ResponseEntity.ok(response);
	}

	//====================================
	// 学習記録詳細
	//====================================
	@GetMapping
	public ResponseEntity<Page<StudyLogResponseDto>> getLogs(
	        @RequestParam String userId,
	        @RequestParam(required = false) String keyword,
	        @RequestParam(required = false) String priority,
	        @RequestParam(required = false) String status,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size
	) {

	    Page<StudyLogResponseDto> result;

	    if(keyword != null || priority != null || status != null) {

	        result = studyLogService.searchLogs(
	                userId,
	                keyword,
	                priority,
	                status,
	                page,
	                size
	        );

	    } else {

	        result = studyLogService.getLogs(
	                userId,
	                page,
	                size
	        );
	    }

	    return ResponseEntity.ok(result);
	}
	
	//======================================
	// 学習記録削除
	//======================================
	@DeleteMapping("/{userId}/{id}")
	public ResponseEntity<Void> delete(
			@PathVariable String userId,
			@PathVariable Long id
	) {
		
		studyLogService.delete(userId, id);
		
		return ResponseEntity.noContent().build();
	}
}
