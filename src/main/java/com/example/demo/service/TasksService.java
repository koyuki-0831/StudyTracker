package com.example.demo.service;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TasksRequestDto;
import com.example.demo.dto.TasksResponseDto;
import com.example.demo.model.TasksModel;
import com.example.demo.repository.TasksRepository;

@Service
public class TasksService {
	
	private final TasksRepository tasksRepository;
	
	public TasksService(TasksRepository tasksRepository) {
		this.tasksRepository = tasksRepository;
	}
	
	//----- タスク一覧取得 ------
	public Page<TasksResponseDto> getTasks(
			String userId,
			String mode,
			String keyword,
			String priority,
			String status,
			int page,
			int size,
			String sortBy,
			String direction
	){
		
		//ソート生成
		Sort sort = Sort.by(
				"asc".equalsIgnoreCase(direction)
						? Sort.Direction.ASC
						: Sort.Direction.DESC,
				sortBy
		);
		
		Pageable pageable = PageRequest.of(page, size, sort);
		
		//modeで分岐(visibikity制御)
		Boolean isMy = "MY".equalsIgnoreCase(mode);
		
		Page<TasksModel> result = isMy
				? tasksRepository.searchMyTasks(userId, keyword, priority, status, pageable)
				: tasksRepository.searchAllTasks(userId, keyword, priority, status, pageable);
		
		return result.map(this::toResponseDto);
				
	}
	
	//------ 新規タスク作成 ---------- 
	public TasksResponseDto createTasks(String userId,TasksRequestDto dto) {
		
		//バリデーション
		validate(dto);
		
		// DTO → Model
		TasksModel model = new TasksModel();
		model.setUserId(userId);
		model.setTitle(dto.getTitle());
		model.setDescription(dto.getDescription());
		model.setPriority(dto.getPriority());
		model.setDue_date(dto.getDue_date());
		model.setVisibility(dto.getVisibility());
		
		//デフォルト
		if(model.getVisibility() == null || model.getVisibility().isBlank()) {
			model.setVisibility("PUBLIC");
		}
		
		TasksModel saved = tasksRepository.save(model);
		
		// Model → DTO
		return toResponseDto(saved);
	}
	
	//-------- タスク更新 ---------
		public TasksResponseDto updateTasks(Long id, String userId, TasksRequestDto dto) {
		
		//対象取得(必ずIDで一意に)
		TasksModel tasks = tasksRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("タスクが存在していません"));
		
		//所有者チェック
		if(!Objects.equals(tasks.getUserId(), userId)) {
			throw new IllegalArgumentException("他人のタスクは更新できません");
		}
			
		
		//バリデーション
		validate(dto);
		
		// DTO → Model (上書き)
		tasks.setTitle(dto.getTitle());
		tasks.setDescription(dto.getDescription());
		tasks.setStatus(dto.getStatus());
		
		if(dto.getStatus() == null || dto.getStatus().isBlank()) {
			throw new IllegalArgumentException("進行度は必須です");
		}
		
		if(!List.of("未着手", "進行中", "完了").contains(dto.getStatus())) {
			throw new IllegalArgumentException("statusは 未着手 / 進行中 / 完了 のみです");
		}
		
		tasks.setPriority(dto.getPriority());
		tasks.setDue_date(dto.getDue_date());
		tasks.setVisibility(dto.getVisibility());
		
		//念の為のvisibilityのデフォルト制御
		if(tasks.getVisibility() == null || tasks.getVisibility().isBlank()) {
			tasks.setVisibility("PUBLIC");
		}
		
		
		TasksModel saved = tasksRepository.save(tasks);
		
		//保存
		return toResponseDto(saved);
	}
	
	//---------- タスク削除　----------
	public void deleteTasks(Long id, String userId) {
		 //存在チェック
		TasksModel tasks = tasksRepository.findById(id) 
			.orElseThrow(() -> new IllegalArgumentException("指定のタスクは存在していません"));
		//所有者チェック
		if(!tasks.getUserId().equals(userId)) {
			throw new IllegalArgumentException("他人のタスクは削除できません");
		}
		tasksRepository.delete(tasks);
	}
	
	//共通バリデーション
	private void validate(TasksRequestDto dto) {
		
		if(dto.getTitle() == null || dto.getTitle().isBlank()) {
			throw new IllegalArgumentException("タイトルは必須です");
		}
		if(dto.getPriority() == null || dto.getPriority().isBlank()) {
			throw new IllegalArgumentException("優先度は必須です");
		}
		if(!List.of("低", "中", "高").contains(dto.getPriority())) {
			throw new IllegalArgumentException("priorityは 低 / 中 / 高 のみです");
		}
		if("高".equals(dto.getPriority()) && dto.getDue_date() == null) {
			throw new IllegalArgumentException("優先度が「高」の場合は期限必須です");
		}
		if(dto.getVisibility() != null && !List.of("PUBLIC","PRIVATE").contains(dto.getVisibility())) {
			throw new IllegalArgumentException("visibility は PUBLIC / PRIVATE のみです");
		}
	}
	
	// Model　→　Response　の変換
	private TasksResponseDto toResponseDto(TasksModel model) {
		
		TasksResponseDto dto = new TasksResponseDto();
		
		dto.setId(model.getId());
		dto.setUserId(model.getUserId());
		dto.setTitle(model.getTitle());
		dto.setDescription(model.getDescription());
		dto.setStatus(model.getStatus());
		dto.setPriority(model.getPriority());
		dto.setDue_date(model.getDue_date());
		dto.setVisibility(model.getVisibility());
		dto.setCreated_at(model.getCreated_at());
		dto.setUpdate_date(model.getUpdate_date());
		
		return dto;
		
	}

//タスク検索を一覧表示処理と統合したのでこめんとアウト
//	//-------- タスク検索　--------------
//	public List<TasksModel> searchTasks(
//			String userId,
//			String keyword,
//			String priority,
//			String mode) {
//		
//		//未入力の場合→い一覧処理へ
//		if ((keyword == null || keyword.isBlank()) &&
//			(priority == null || priority.isBlank())) {
//			
//			return getTasks(userId, mode);
//		}
//		
//		List<TasksModel> tasks;
//		
//		if("MY".equalsIgnoreCase(mode)) {
//			
//			//自分のみ
//			tasks = tasksRepository.searchMyTasks(
//					userId,
//					keyword,
//					priority,
//					Sort.by(Sort.Direction.DESC, "created_at")
//					);
//		} else {
//			
//			//自分+PUBLIC
//			tasks = tasksRepository.searchAllTasks(
//					userId,
//					keyword,
//					priority,
//					Sort.by(Sort.Direction.DESC, "created_at")
//					);
//		}
//		
//		return tasks;
//	}
	
}