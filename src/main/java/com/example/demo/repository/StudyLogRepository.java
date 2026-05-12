package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.StudyLogModel;

public interface StudyLogRepository extends JpaRepository<StudyLogModel, Long> {
	
	//=================================
	//🔹基本取得
	//=================================
	
	//ユーザー単位で一覧（ページリング）
	Page<StudyLogModel> findByUserId(String userId, Pageable pageable);
	
	//ユーザー単位で１件取得
	Optional<StudyLogModel> findByUserId(String userId, int page, int size);
	Optional<StudyLogModel> findByUserIdAndTasks_Id(String userId, Long taskId);
	
	//タスク単位で一覧
	List<StudyLogModel> findByTasks_Id(Long taskId);
	
	//=================================
	//🔹検索系
	//=================================
	
	//ステータス検索
	Page<StudyLogModel> findByUserIdAndStatus(String userId, String atatus, Pageable pageable);
	
	//日付範囲検索
	@Query("""
		SELECT s FROM StudyLogModel s
		WHERE s.userId = :userId
		AND s.studyDate BETWEEN :start AND :end
	""")
	Page<StudyLogModel> findByDateRange(
			@Param("userId") String userId,
			@Param("start") LocalDate start,
			@Param("end") LocalDate end,
			Pageable pageable
	);
	
	//キーワード検索（学習内容）
	@Query("""
		SELECT s FROM StudyLogModel s
		WHERE s.userId = :userId
		AND LOWER(s.studyMethod) LIKE LOWER(CONCAT('%', :keyword, '%')) 
	""")
	Page<StudyLogModel> searchByKeyword(
			@Param("userId") String userId,
			@Param("keyword") String keyword,
			Pageable pegeable
	);
	
	//==============================
	//🔹集計（超重要）
	//==============================
	
	// タスクごとの合計時間（分）
	@Query("""
		SELECT COALESCE(SUM(s.durationMinutes), 0)
		FROM StudyLogModel s
		WHERE s.tasks.id = :taskId
	""")
	Integer sumDurationByTaskId(@Param("taskId") Long taskId);
	
	// ユーザー全体の合計時間（分）
	@Query("""
			SELECT COALESCE(SUM(s.durationMinutes), 0)
			FROM StudyLogModel s
			WHERE s.userId = :userId
	""")
	Integer sumDurationByUser(@Param("userId") String userId);

}
