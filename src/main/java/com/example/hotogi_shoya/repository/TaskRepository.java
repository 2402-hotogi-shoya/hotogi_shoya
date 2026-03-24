package com.example.hotogi_shoya.repository;

import com.example.hotogi_shoya.repository.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT t FROM Task t " +
            "WHERE t.limitDate BETWEEN :startDate AND :endDate " +
            "AND (:filterText IS NULL OR t.content = :filterText) " +
            "AND (:status IS NULL OR t.status = :status) " +
            "ORDER BY t.limitDate ASC")
    List<Task> findTasksReport(@Param("startDate") LocalDate startDate,
                               @Param("endDate") LocalDate endDate,
                               @Param("filterText") String filterText,
                               @Param("status") Short status);

}
