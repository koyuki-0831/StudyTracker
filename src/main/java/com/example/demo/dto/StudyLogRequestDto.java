package com.example.demo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class StudyLogRequestDto {
	
	
	@NotNull
	private String studyMethod;
	
	private String issue;
	private String cause;
	private String solution;
	private String memo;
	
	@NotNull
	@Min(1)
	@Max(5)
	private Integer comprehensionLevel;
	
	@NotNull
	@Min(1)
	@Max(1440)
	private Integer durationMinutes;
	
	private LocalDate finishDate;
	
	@NotNull
	private String status;
	
	// getter / setter 

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

	public LocalDate getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(LocalDate finishDate) {
		this.finishDate = finishDate;
	}
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

}
