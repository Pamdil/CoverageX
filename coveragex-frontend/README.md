# CoverageX Frontend (v2)
Matches the mockup. Shows only the **5 most recent** pending tasks; clicking **Done** hides a task immediately and calls the backend to mark it completed.

## API
- GET  `/api/tasks`
- POST `/api/tasks` body: `{ "title": string, "description": string }`
- Mark done tries: `POST /api/tasks/{id}/complete` then fallback `PUT /api/tasks/{id}` with `{ "completed": true }`.

## Configure base URL
Create `.env` in project root, e.g.:
```
VITE_API_BASE_URL=http://localhost:8000
```

## Run
```bash
npm i
npm run dev
```
