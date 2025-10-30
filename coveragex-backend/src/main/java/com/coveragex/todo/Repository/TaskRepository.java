package com.coveragex.todo.Repository;

import com.coveragex.todo.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTop5ByCompletedFalseOrderByCreatedAtDesc();
}
