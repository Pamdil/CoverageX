package com.coveragex.todo.Service;

import com.coveragex.todo.Dto.TaskCreateRequest;
import com.coveragex.todo.Entity.Task;
import com.coveragex.todo.Exception.NotFoundException;
import com.coveragex.todo.Repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    private final TaskRepository repo = mock(TaskRepository.class);
    private final TaskServiceImpl service = new TaskServiceImpl(repo);

    @Test
    void create_shouldPersist_andReturnDto() {
        TaskCreateRequest req = new TaskCreateRequest();
        req.setTitle("T"); req.setDescription("D");

        Task saved = new Task();
        saved.setId(1L); saved.setTitle("T"); saved.setDescription("D"); saved.setCompleted(false);
        when(repo.save(any(Task.class))).thenReturn(saved);

        var resp = service.create(req);

        ArgumentCaptor<Task> cap = ArgumentCaptor.forClass(Task.class);
        verify(repo).save(cap.capture());
        assertEquals("T", cap.getValue().getTitle());
        assertFalse(cap.getValue().isCompleted());
        assertEquals(1L, resp.getId());
    }

    @Test
    void listRecentActive_returnsMappedDtos() {
        Task t = new Task(); t.setId(5L); t.setTitle("A"); t.setDescription("B"); t.setCompleted(false);
        when(repo.findTop5ByCompletedFalseOrderByCreatedAtDesc()).thenReturn(List.of(t));

        var list = service.listRecentActive();
        assertEquals(1, list.size());
        assertEquals(5L, list.get(0).getId());
    }

    @Test
    void markCompleted_marksTrue_orThrow404() {
        Task t = new Task(); t.setId(2L); t.setTitle("x"); t.setCompleted(false);
        when(repo.findById(2L)).thenReturn(Optional.of(t));

        var r = service.markCompleted(2L);
        assertTrue(r.isCompleted());
        verify(repo).save(t);

        when(repo.findById(9L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.markCompleted(9L));
    }
}
