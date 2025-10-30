// Configure with .env if needed: VITE_API_BASE_URL=http://localhost:8080
const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8000'

async function http(path, opts = {}) {
  const res = await fetch(`${BASE_URL}${path}`, {
    headers: { 'Content-Type': 'application/json', ...(opts.headers || {}) },
    ...opts,
  })
  if (!res.ok) {
    const text = await res.text()
    throw new Error(text || `HTTP ${res.status}`)
  }
  const ct = res.headers.get('content-type') || ''
  if (ct.includes('application/json')) return res.json()
  return res.text()
}

export function getTasks() {
  return http('/api/tasks', { method: 'GET' })
}

export function createTask(body) {
  return http('/api/tasks', { method: 'POST', body: JSON.stringify(body) })
}

// Mark completed = true via PUT /api/tasks/{id}
export function markDone(id) {
  return http(`/api/tasks/${id}/complete`, {
    method: 'PUT',
    body: JSON.stringify({ completed: true })
  })
}


