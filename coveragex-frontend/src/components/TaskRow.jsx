import React from 'react'

export default function TaskRow({ task, onDone }) {
  return (
    <div className="task-row">
      <div className="task-text">
        <div className="task-title">{task.title}</div>
        <div className="task-desc">{task.description}</div>
      </div>
      <button className="btn-hollow" onClick={onDone}>Done</button>
    </div>
  )
}
