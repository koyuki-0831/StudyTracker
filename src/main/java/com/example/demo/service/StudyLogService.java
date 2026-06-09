package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	
	public StudyLogService(StudyLogRepository studyLogRepository,
							TasksRepository taskksRepository, TasksRepository tasksRepository) {
		this.studyLogRepository = studyLogRepository;
		this.tasksRepository = tasksRepository;
	}
	
	//==================================
	//🔹作成
	//==================================
	@Transactional
	public StudyLogResponseDto create(String userId, long taskId, StudyLogRequestDto dto) {
		TasksModel task = tasksRepository.findById(taskId)
		        .orElseThrow(() -> new IllegalArgumentException("Taskが存在しません"));
		 
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

		 if("完了".equals(dto.getStatus())) {
		     log.setFinishDate(LocalDate.now());
		 } else {
		     log.setFinishDate(null);
		 }
		 
		 studyLogRepository.save(log);
		 tasksRepository.save(task);
		 
		 return toDto(log); 
	}
	
	//===============================
	//🔹更新
	//===============================
	@Transactional
	public StudyLogResponseDto update(String userId, Long id, StudyLogUpdateDto dto) {

	    System.out.println("userId=" + userId);
	    System.out.println("id=" + id);
	    System.out.println("dto.status=" + dto.getStatus());
	    System.out.println("dto.durationMinutes=" + dto.getDurationMinutes());
	    System.out.println("dto.comprehensionLevel=" + dto.getComprehensionLevel());

	    StudyLogModel log = studyLogRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("StudyLogが存在しません"));

	    if (!log.getUserId().equals(userId)) {
	    	throw new IllegalArgumentException("他人の学習記録は更新できません");
	    }

	    if (dto.getStudyMethod() != null) log.setStudyMethod(dto.getStudyMethod());
	    if (dto.getIssue() != null) log.setIssue(dto.getIssue());
	    if (dto.getCause() != null) log.setCause(dto.getCause());
	    if (dto.getSolution() != null) log.setSolution(dto.getSolution());
	    if (dto.getMemo() != null) log.setMemo(dto.getMemo());
	    if (dto.getComprehensionLevel() != null) log.setComprehensionLevel(dto.getComprehensionLevel());
	    if (dto.getDurationMinutes() != null) log.setDurationMinutes(dto.getDurationMinutes());

	    if (dto.getStatus() != null) {
	        log.setStatus(dto.getStatus());

	        if ("完了".equals(dto.getStatus())) {
	            log.setFinishDate(LocalDate.now());
	        } else {
	            log.setFinishDate(null);
	        }

	        TasksModel task = log.getTasks();
	        task.setStatus(dto.getStatus());
	        tasksRepository.save(task);
	    }

	    StudyLogModel saved =
	            studyLogRepository.save(log);

	    return toDto(saved);
	}
	
	//============================
	// 🔹 一覧取得（ページリング）
	//============================
	public Page<StudyLogResponseDto> getLogs(String userId, int page, int size){

	    Pageable pageable =
	            PageRequest.of(page, size, Sort.by("id").descending());

	    Page<StudyLogModel> logs =
	            studyLogRepository.findVisibleLogs(userId, pageable);

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
	
	public Page<StudyLogResponseDto> searchLogs(
	        String userId,
	        String keyword,
	        String priority,
	        String status,
	        int page,
	        int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		
	    return studyLogRepository.searchLogs(
	            userId,
	            keyword,
	            priority,
	            status,
	            pageable
	    ).map(this::toDto);
	}
	
	//===================================
	// 🔹学習記録削除
	//===================================
	@Transactional
	public void delete(String userId, Long id) {
		
		StudyLogModel log = studyLogRepository.findById(id)
		        .orElseThrow(() -> new IllegalArgumentException("StudyLogが存在しません"));
		
		//所有者チェック
		if(!log.getUserId().equals(userId)) {
			throw new IllegalArgumentException("他人の学習記録は削除できません");
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
		dto.setStatus(log.getStatus());
		dto.setFinishDate(log.getFinishDate());
		
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

	    if(minutes == null || minutes < 0) {
	        return "0分";
	    }

	    int h = minutes / 60;
	    int m = minutes % 60;

	    return h > 0 ? h + "時間" + m + "分" : m + "分";
	}
	
	private String formatTotalTime(Integer minutes) {
	    if (minutes == null) {
	        return "0.0h";
	    }

	    double hours = minutes / 60.0;
	    return String.format("%.1fh", hours);
	}
	
	private String formatUnderstanding(Integer level) {

	    int lv = level == null ? 0 : level;

	    lv = Math.max(0, Math.min(5, lv));

	    return "★★★★★".substring(0, lv)
	            + "☆☆☆☆☆".substring(0, 5 - lv);
	}
	
	private static final DateTimeFormatter DATE_FORMATTER =
	        DateTimeFormatter.ofPattern("yyyy/MM/dd");
	
	private String formatFinishDate(StudyLogModel log) {

	    if(log.getFinishDate() == null) {
	        return "-";
	    }

	    return log.getFinishDate().format(DATE_FORMATTER);
	}
	}
