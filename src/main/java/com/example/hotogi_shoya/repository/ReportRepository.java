package com.example.hotogi_shoya.repository;

import com.example.hotogi_shoya.repository.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Task, Integer> {
}
