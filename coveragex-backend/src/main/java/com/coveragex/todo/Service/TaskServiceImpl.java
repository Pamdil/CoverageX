package com.coveragex.todo.Service;

import com.coveragex.todo.Dto.TaskCreateRequest;
import com.coveragex.todo.Dto.TaskResponse;
import com.coveragex.todo.Entity.Task;

import com.coveragex.todo.Exception.NotFoundException;
import com.coveragex.todo.Repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repo;

    public TaskServiceImpl(TaskRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public TaskResponse create(TaskCreateRequest req) {
        Task t = new Task();
        t.setTitle(req.getTitle());
        t.setDescription(req.getDescription());
        t.setCompleted(false);
        Task saved = repo.save(t);
        return map(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> listRecentActive() {
        return repo.findTop5ByCompletedFalseOrderByCreatedAtDesc()
                .stream().map(this::map).toList();
    }

    @Override
    @Transactional
    public TaskResponse markCompleted(Long id) {
        Task t = repo.findById(id).orElseThrow(() ->
                new NotFoundException("Task not found: " + id));
        if (!t.isCompleted()) {
            t.setCompleted(true);
            repo.save(t);
        }
        return map(t);
    }

    private TaskResponse map(Task t) {
        return new TaskResponse(t.getId(), t.getTitle(), t.getDescription(), t.isCompleted(), t.getCreatedAt());
    }
}
