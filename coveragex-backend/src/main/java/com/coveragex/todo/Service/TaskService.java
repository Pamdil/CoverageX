package com.coveragex.todo.Service;

import com.coveragex.todo.Dto.TaskCreateRequest;
import com.coveragex.todo.Dto.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse create(TaskCreateRequest req);
    List<TaskResponse> listRecentActive();
    TaskResponse markCompleted(Long id);
}
