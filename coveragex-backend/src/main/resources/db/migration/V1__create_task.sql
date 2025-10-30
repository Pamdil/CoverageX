-- src/main/resources/db/migration/V1__create_task.sql
CREATE TABLE IF NOT EXISTS task (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  description TEXT NOT NULL,
  completed BOOLEAN NOT NULL DEFAULT FALSE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
CREATE INDEX IF NOT EXISTS idx_task_incomplete_recent
  ON task (created_at DESC)
  WHERE completed = FALSE;
