package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "study_logs")
public class StudyLogModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	//　ユーザーID
	@Column(nullable = false)
	private String userId;
	
	//　Taskとのリレーション
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id" , nullable = false)
	private TasksModel tasks;
	
	// 学習内容
	@Column(nullable = false , columnDefinition = "TEXT")
	private String studyMethod;
	
	// 発生した問題
	@Column(columnDefinition = "TEXT")
	private String issue;
	
	// 原因
	@Column(columnDefinition = "TEXT")
	private String cause;
	
	// 解決方法
	@Column(columnDefinition = "TEXT")
	private String solution;
	
	// メモ
	@Column(columnDefinition = "TEXT")
	private String memo;
	
	// 理解度
	@Min(1)
	@Max(5)
	@Column(nullable = false)
	private Integer comprehensionLevel;
	
	// 学習時間（分）
	@Column(nullable = false)
	private Integer durationMinutes;
	
	//検索用学習時間
	@Column(name = "study_date")
	private LocalDate studyDate;
	
	// ★ 状態（履歴として持つ）
	@Column(nullable = false)
	private String status;
	
	//作成日時
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime created_at;
	
	//更新日時
	@UpdateTimestamp
	private LocalDateTime update_date;
	
	// --- ライフサークル ---
	@PrePersist
	public void onCreate() {
		this.created_at = LocalDateTime.now();
	}
	
	public void onUpdate() {
		this.update_date = LocalDateTime.now();
	}
	
	// getter / setter
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public TasksModel getTasks() {
		return tasks;
	}

	public void setTasks(TasksModel tasks) {
		this.tasks = tasks;
	}

	public String getStudyMethod() {
		return studyMethod;
	}

	public void setStudyMethod(String studyMethod) {
		this.studyMethod = studyMethod;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getComprehensionLevel() {
		return comprehensionLevel;
	}

	public void setComprehensionLevel(Integer comprehensionLevel) {
		this.comprehensionLevel = comprehensionLevel;
	}

	public Integer getDurationMinutes() {
		return durationMinutes;
	}

	public void setDurationMinutes(Integer durationMinutes) {
		this.durationMinutes = durationMinutes;
	}

	public LocalDate getStudyDate() {
		return studyDate;
	}

	public void setStudyDate(LocalDate studyDate) {
		this.studyDate = studyDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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