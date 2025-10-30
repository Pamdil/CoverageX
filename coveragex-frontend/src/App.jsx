import React, { useEffect, useMemo, useState } from 'react'
import { getTasks, createTask, markDone } from './services/api'
import TaskRow from './components/TaskRow'

export default function App() {
  const [tasks, setTasks] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [form, setForm] = useState({ title: '', description: '' })
  const [creating, setCreating] = useState(false)

  useEffect(() => { load() }, [])

  async function load() {
    try {
      setLoading(true)
      setError('')
      const data = await getTasks()
      setTasks(Array.isArray(data) ? data : [])
    } catch (e) {
      setError('Load failed')
    } finally {
      setLoading(false)
    }
  }

  const visibleTasks = useMemo(() => {
    // show only NOT completed, newest 5
    const pending = tasks.filter(t => !t.completed)
    const getDate = (t) => {
      const d = t.createdAt || t.created_at
      return d ? new Date(d).getTime() : 0
    }
    pending.sort((a,b) => getDate(b) - getDate(a) || (b.id || 0) - (a.id || 0))
    return pending.slice(0, 5)
  }, [tasks])

  async function onSubmit(e) {
    e.preventDefault()
    if (!form.title.trim() || !form.description.trim()) return
    try {
      setCreating(true)
      await createTask(form)
      setForm({ title: '', description: '' })
      await load()
    } catch (e) {
      alert(e.message || 'Failed to create task')
    } finally {
      setCreating(false)
    }
  }

  async function onDone(id) {
    // Optimistic: remove from UI immediately
    const prev = tasks
    setTasks(prev.filter(t => t.id !== id))
    try {
      await markDone(id) // persist completed=true
    } catch (e) {
      // rollback if API fails
      setTasks(prev)
      alert('Failed to mark as done')
    }
  }

  return (
    <div className="shell">
      <div className="app-frame">
        <div className="left-pane">
          <h2>Add a Task</h2>
          <form onSubmit={onSubmit} className="form-card">
            <div className="field">
              <label>Title</label>
              <input
                value={form.title}
                onChange={(e) => setForm({ ...form, title: e.target.value })}
                placeholder="Title"
                maxLength={200}
                required
              />
            </div>
            <div className="field">
              <label>Description</label>
              <textarea
                value={form.description}
                onChange={(e) => setForm({ ...form, description: e.target.value })}
                placeholder="Description"
                rows={4}
                required
              />
            </div>
            <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
  <button type="submit" className="btn-primary" disabled={creating}>
    {creating ? 'Adding…' : 'Add'}
  </button>
</div>

          </form>
        </div>

        <div className="divider" />

        <div className="right-pane">
          {loading && <p className="muted">Loading…</p>}
          {error && <p className="error">{error}</p>}
          {!loading && !error && visibleTasks.length === 0 && <p className="muted">No tasks found.</p>}

          <div className="task-list">
            {visibleTasks.map(t => (
              <TaskRow key={t.id} task={t} onDone={() => onDone(t.id)} />
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}
