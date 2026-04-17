package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.TasksModel;

public interface TasksRepository extends JpaRepository<TasksModel, Long> {
//全部searchMyTasks / searchAllTasksで代替たのでコメントアウト
//	List<TasksModel> findByPriority(String priority);
//	List<TasksModel> findByTitleContainingOrDescriptionContaining(String title, String description);
//	List<TasksModel> findByTitleContainingOrDescriptionContainingAndPriority(
//		    String title,
//		    String description,
//		    String priority
//		);
//	List<TasksModel> findByUserId(String userId, Sort sort);
//	List<TasksModel> findByUserIdOrVisibility(String userId, String visibility, Sort sort);
	
	//MY用
	@Query("""
			SELECT t FROM TasksModel t
			WHERE t.userId = :userId 
			AND (:keyword IS NULL OR TRIM(:keyword) = '' OR
			     (t.title LIKE %:keyword% OR t.description LIKE %:keyword%))
			AND (:priority IS NULL OR :priority = '' OR
			     t.priority = :priority)
			AND (:status IS NULL OR :status = '' OR
				 t.status = :status)
			""")
		Page<TasksModel>searchMyTasks (
				@Param("userId") String userId,
				@Param("keyword") String keyword,
				@Param("priority") String priority,
				@Param("status") String status,
				Pageable pageable
	);
	
	//ALL用
	@Query("""
			SELECT t FROM TasksModel t
			WHERE (t.userId = :userId OR t.visibility = 'PUBLIC')
			AND (:keyword IS NULL OR :keyword = '' OR
			     t.title LIKE %:keyword% OR t.description LIKE %:keyword%)
			AND (:priority IS NULL OR :priority = '' OR
			     t.priority = :priority)
			AND (:status IS NULL OR :status = '' OR
				 t.status = :status)
			""")
			Page<TasksModel>searchAllTasks(
					@Param("userId") String userId,
					@Param("keyword") String keyword,
					@Param("priority") String priority,
					@Param("status") String status,
					Pageable pageable
			);
	
	
}
