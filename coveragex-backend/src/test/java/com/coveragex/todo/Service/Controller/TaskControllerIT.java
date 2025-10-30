package com.coveragex.todo.Service.Controller;

import com.coveragex.todo.Dto.TaskCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)


class TaskControllerIT {

    @Autowired
    private com.coveragex.todo.Repository.TaskRepository repo;

    @BeforeEach
    void cleanDatabase() {
        repo.deleteAll();
    }
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @Test
    void create_list_complete_flow() throws Exception {
        // create
        TaskCreateRequest req = new TaskCreateRequest();
        req.setTitle("Buy milk"); req.setDescription("Kandy");
        mvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.completed").value(false));

        // list (should contain 1)
        mvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Buy milk"));

        // complete
        mvc.perform(put("/api/tasks/{id}/complete", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));

        // list again (should be empty: completed hidden)
        mvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
