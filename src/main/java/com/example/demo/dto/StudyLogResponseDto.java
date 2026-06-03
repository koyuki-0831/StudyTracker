package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudyLogResponseDto {
	
	// ----- StudyLog -----
	private Long id;
	private Long taskId;
	private String userId;
	private String studyMethod;
	private String issue;
	private String cause;
	private String solution;
	private String memo;
	private Integer comprehensionLevel;
	private Integer durationMinutes;
	private LocalDate finishDate;
	private String status;
	
	// ----- Task -----
	private String taskTitle;
	private String description;
	private String priority;
	private String taskStatus;
	private String visibility;
	private LocalDate due_date;
	private LocalDateTime created_at;
	private LocalDateTime update_date;
	
	// ----- 表示用 -----
	private String durationLabel;
	private String understandingLabel;
	private String totalTimeLabel;
	private String finishDateLabel;
	
	// ----- getter / setter -----
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
	public Long getTaskId() { return taskId; }
	public void setTaskId(Long taskId) { this.taskId = taskId; }
	
	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }
	
	public String getStudyMethod() { return studyMethod; }
	public void setStudyMethod(String studyMethod) { this.studyMethod = studyMethod; }
	
	public String getIssue() { return issue; }
	public void setIssue(String issue) { this.issue = issue; }
	
	public String getCause() { return cause; }
	public void setCause(String cause) { this.cause = cause; }
	
	public String getSolution() { return solution; }
	public void setSolution(String solution) { this.solution = solution; }
	
	public String getMemo() { return memo; }
	public void setMemo(String memo) { this.memo = memo; }
	
	public Integer getComprehensionLevel() { return comprehensionLevel; }
	public void setComprehensionLevel(Integer comprehensionLevel) { this.comprehensionLevel = comprehensionLevel; }
	
	public Integer getDurationMinutes() { return durationMinutes; }
	public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
	
	public LocalDate getFinishDate() { return finishDate; }
	public void setFinishDate(LocalDate finishDate) { this.finishDate = finishDate; }
	
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	
	public String getTaskTitle() { return taskTitle; }
	public void setTaskTitle(String taskTitle) { this.taskTitle = taskTitle; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public String getPriority() { return priority; }
	public void setPriority(String priority) { this.priority = priority; }
	
	public String getTaskStatus() { return taskStatus; }
	public void setTaskStatus(String taskStatus) { this.taskStatus = taskStatus; }
	
	public String getVisibility() { return visibility; }
	public void setVisibility(String visibility) { this.visibility = visibility; }
	
	public LocalDate getDue_date() { return due_date; }
	public void setDue_date(LocalDate due_date) { this.due_date = due_date; }
	
	public LocalDateTime getCreated_at() { return created_at; }
	public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
	
	public LocalDateTime getUpdate_date() { return update_date; }
	public void setUpdate_date(LocalDateTime update_date) { this.update_date = update_date; }
	
	public String getDurationLabel() { return durationLabel; }
	public void setDurationLabel(String durationLabel) { this.durationLabel = durationLabel; }
	
	public String getUnderstandingLabel() { return understandingLabel; }
	public void setUnderstandingLabel(String understandingLabel) { this.understandingLabel = understandingLabel; }
	
	public String getTotalTimeLabel() { return totalTimeLabel; }
	public void setTotalTimeLabel(String totalTimeLabel) { this.totalTimeLabel = totalTimeLabel; }
	
	public String getFinishDateLabel() { return finishDateLabel; }
	public void setFinishDateLabel(String finishDateLabel) { this.finishDateLabel = finishDateLabel; }
	
}
