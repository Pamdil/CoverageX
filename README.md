CoverageX – Full Stack To-Do Web Application
This project is a small to-do list web application built for the Full Stack Engineer Take-Home Assessment.
It lets users add tasks, view only the five most recent incomplete tasks, and mark tasks as completed.
All components run together in Docker using Docker Compose.
1. Project Overview
The system has three main parts:
Database – PostgreSQL stores all task information.
Backend API – Java Spring Boot built with Maven. It provides REST API endpoints to create, list, and complete tasks.
Frontend UI – React (Vite) single-page application served through Nginx.
2. Key Features
Create a new task with a title and description
Display only the five most recent incomplete tasks
Mark a task as done so it disappears from the list
Containerized backend, frontend, and database using Docker Compose
Example integration and unit tests included
3. Folder Structure
CoverageX/
├── COVERAGEX-BACKEND/     # Spring Boot API + Docker Compose
│   ├── Dockerfile
│   ├── docker-compose.yml
│   └── src/...
└── COVERAGEX-FRONTEND/    # React (Vite) frontend + Nginx server
    └── Dockerfile
4. Prerequisites
Docker Desktop or Docker Engine with Compose v2
Internet access for the first build (to pull images and dependencies)
5. How to Run
Open a terminal and run the following commands:
cd COVERAGEX-BACKEND
docker compose up --build
Once the build finishes:
Frontend: http://localhost:5173
Backend API: http://localhost:8000/api/tasks
Database: PostgreSQL on localhost :5432
(user = postgres, password = postgres, database = todo)
To stop everything:
docker compose down
6. API Guide
Base URL: http://localhost:8000/api/tasks
Method	Endpoint	Description
GET	/api/tasks	Returns up to five most recent incomplete tasks
POST	/api/tasks	Creates a new task
PUT	/api/tasks/{id}/complete	Marks a task as completed
Example request:
curl -X POST http://localhost:8000/api/tasks \
     -H "Content-Type: application/json" \
     -d '{"title":"Sample Task","description":"Testing API"}'
7. Running Tests
Backend Tests
From the backend folder:
mvn test
Example integration test file
src/test/java/com/coveragex/todo/TaskControllerIT.java:
@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIT {
  @Autowired MockMvc mvc;

  @Test
  void createAndCompleteTask() throws Exception {
    mvc.perform(post("/api/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"title\":\"Task1\",\"description\":\"Demo\"}"))
      .andExpect(status().isCreated());

    mvc.perform(get("/api/tasks"))
      .andExpect(status().isOk());

    mvc.perform(put("/api/tasks/{id}/complete", 1))
      .andExpect(status().isOk());
  }
}
Frontend Tests (optional)
Install and run Vitest:
npm install --save-dev vitest @testing-library/react @testing-library/jest-dom jsdom
npm test
8. Environment Variables
These values are configured in docker-compose.yml.
Backend:
DATABASE_URL=jdbc:postgresql://db:5432/todo
DB_USER=postgres
DB_PASSWORD=postgres
APP_PORT=8000
Frontend:
VITE_API_BASE_URL=http://api:8000/api/tasks
9. Troubleshooting
If the frontend cannot reach the backend, check VITE_API_BASE_URL in docker-compose.yml.
If the backend cannot connect to the database, verify the DATABASE_URL.
To rebuild everything after changes:
docker compose up --build
