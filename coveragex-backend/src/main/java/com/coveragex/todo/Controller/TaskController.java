package com.coveragex.todo.Controller;

import com.coveragex.todo.Dto.TaskCreateRequest;
import com.coveragex.todo.Dto.TaskResponse;
import com.coveragex.todo.Service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:3000","*"})
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    // Create a task
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@RequestBody @Valid TaskCreateRequest req) {
        return service.create(req);
    }

    // List latest 5 incomplete tasks (for UI)
    @GetMapping
    public List<TaskResponse> listRecentActive() {
        return service.listRecentActive();
    }

    // Mark a task completed -> then UI should not show it
    @PutMapping("/{id}/complete")
    public TaskResponse complete(@PathVariable Long id) {
        return service.markCompleted(id);
    }
}
