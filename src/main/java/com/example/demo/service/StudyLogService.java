package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.StudyLogRequestDto;
import com.example.demo.dto.StudyLogResponseDto;
import com.example.demo.dto.StudyLogUpdateDto;
import com.example.demo.model.StudyLogModel;
import com.example.demo.model.TasksModel;
import com.example.demo.repository.StudyLogRepository;
import com.example.demo.repository.TasksRepository;

@Service
public class StudyLogService {
	
	private final StudyLogRepository studyLogRepository;
	private final TasksRepository tasksRepository;
	
	public StudyLogService(StudyLogRepository studyLogREpository,
							TasksRepository taskksRepository, StudyLogRepository studyLogRepository, TasksRepository tasksRepository) {
		this.studyLogRepository = studyLogRepository;
		this.tasksRepository = tasksRepository;
	}
	
	//==================================
	//🔹作成
	//==================================
	@Transactional
	public StudyLogResponseDto create(String userId, long taskId, StudyLogRequestDto dto) {
		 TasksModel task = tasksRepository.findById(taskId)
				 .orElseThrow(() -> new RuntimeException("Task not found"));
		 
		 StudyLogModel log = new StudyLogModel();
		 log.setUserId(userId);
		 log.setTasks(task);
		 
		 log.setStudyMethod(dto.getStudyMethod());
		 log.setIssue(dto.getIssue());
		 log.setCause(dto.getCause());
		 log.setSolution(dto.getSolution());
		 log.setMemo(dto.getMemo());
		 log.setComprehensionLevel(dto.getComprehensionLevel());
		 log.setDurationMinutes(dto.getDurationMinutes());
		 log.setStatus(dto.getStatus());
		 
		 // ★　Taskのstatus同期
		 task.setStatus(dto.getStatus());
		 
		 log.setCreated_at(LocalDateTime.now());
		 log.setUpdate_date(LocalDateTime.now());
		 
		 studyLogRepository.save(log);
		 
		 return toDto(log); 
	}
	
	//===============================
	//🔹更新
	//===============================
	@Transactional
	public StudyLogResponseDto update(String userId, Long taskId, StudyLogUpdateDto dto) {
		StudyLogModel log = studyLogRepository.findByUserIdAndTasks_Id(userId, taskId)
				.orElseThrow(() -> new RuntimeException("StudyLog not found"));
		
		if(dto.getStudyMethod() != null) log.setStudyMethod(dto.getStudyMethod());
		if(dto.getIssue() != null) log.setIssue(dto.getIssue());
		if(dto.getCause() !=null) log.setCause(dto.getCause());
		if(dto.getSolution() !=null) log.setSolution(dto.getSolution());
		if(dto.getMemo() !=null) log.setMemo(dto.getMemo());
		if(dto.getComprehensionLevel() !=null) log.setComprehensionLevel(dto.getComprehensionLevel());
		if(dto.getDurationMinutes() !=null) log.setDurationMinutes(dto.getDurationMinutes());
		
		if(dto.getStatus() !=null) {
			log.setStatus(dto.getStatus());
			
			//★ Task側も更新
			TasksModel task = log.getTasks();
			task.setStatus(dto.getStatus());
		}
		
		return toDto(log);
	}
	
	//============================
	// 🔹 一覧取得（ページリング）
	//============================
	public Page<StudyLogResponseDto> getLogs(String userId, int page, int size){
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		
		Page<StudyLogModel> logs = studyLogRepository.findByUserId(userId,pageable);
		
		return logs.map(this::toDto);
	}
	
	//===================================
	// 🔹学習記録検索
	//===================================
	
	public Page<StudyLogModel> getLogByDateRange(
			String userId,
			LocalDate start,
			LocalDate end
	) {
		
		return studyLogRepository.findByDateRange(
				userId,
				start,
				end,
				Pageable.unpaged()
		);
	}
	
	//===================================
	// 🔹学習記録削除
	//===================================
	@Transactional(readOnly = true)
	public void delate(String userId, Long taskId) {
		
		StudyLogModel log = studyLogRepository
				.findByUserIdAndTasks_Id(userId, taskId)
				.orElseThrow(() -> new RuntimeException("StudyLog not found"));
		
		//所有者チェック
		if(!log.getUserId().equals(userId)) {
			throw new RuntimeException("削除権限がありません");
		}
		
		studyLogRepository.delete(log);
	}
	
	//===================================
	//  🔹DTO変換（核心）
	//===================================
	private StudyLogResponseDto toDto(StudyLogModel log) {
		
		StudyLogResponseDto dto = new StudyLogResponseDto();
		
		//-------- StudyLog ------------
		dto.setId(log.getId());
		dto.setTaskId(log.getTasks().getId());
		dto.setUserId(log.getUserId());
		dto.setStudyMethod(log.getStudyMethod());
		dto.setIssue(log.getIssue());
		dto.setCause(log.getCause());
		dto.setSolution(log.getSolution());
		dto.setMemo(log.getMemo());
		dto.setComprehensionLevel(log.getComprehensionLevel());
		dto.setDurationMinutes(log.getDurationMinutes());
		dto.setTaskStatus(log.getStatus());
		
		//---------- Task -----------------
		TasksModel task = log.getTasks();
		dto.setTaskTitle(task.getTitle());
		dto.setDescription(task.getDescription());
		dto.setPriority(task.getPriority());
		dto.setTaskStatus(task.getStatus());
		dto.setVisibility(task.getVisibility());
		dto.setDue_date(task.getDue_date());
		dto.setCreated_at(task.getCreated_at());
		dto.setUpdate_date(task.getUpdate_date());
		
		//---------- 表示用 -----------------
		dto.setDurationLabel(formatDuration(log.getDurationMinutes()));
		dto.setUnderstandingLabel(formatUnderstanding(log.getComprehensionLevel()));
		
		// ★ totalTime（合計)
		Integer total = studyLogRepository.sumDurationByTaskId(task.getId());
		dto.setTotalTimeLabel(formatTotalTime(total));
		
		// ★ 　finishDate
		dto.setFinishDateLabel(
				"完了".equals(log.getStatus()) 
						? formatFinishDate(log)
						: "-"
				);
		
		return dto;
	}
	
	//======================================
	// 🔹 フォーマット系
	//======================================
	private String formatDuration(Integer minutes) {
		int h = minutes / 60;
		int m = minutes % 60;
		return h > 0 ? h + "時間" + m + "分" : m + "分";
	}
	
	private String formatTotalTime(Integer minutes) {
		double hours = minutes / 60.0;
		return String.format("%.1fh", hours);
	}
	
	private String formatUnderstanding(Integer level) {
		return "★★★★★".substring(0, level) + "☆☆☆☆☆".substring(0, 5 - level);		
	}
	
	private String formatFinishDate(StudyLogModel log) {
		
		if(log.getUpdate_date() != null) {
			return log.getUpdate_date().toLocalTime().toString();
		}
		
		if(log.getCreated_at() != null) {
			return log.getCreated_at().toLocalTime().toString();
		}
		return "-";
	}
	}
