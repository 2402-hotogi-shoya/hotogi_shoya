package com.example.hotogi_shoya.repository;

import com.example.hotogi_shoya.repository.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByOrderByLimitDateAsc();
}
