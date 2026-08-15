import React from 'react';
import { useNavigate } from 'react-router-dom';

export default function ModuleHeader({ eyebrow, title, tagline }) {
  const navigate = useNavigate();
  return (
    <div style={{ marginBottom: 36 }}>
      <button
        type="button"
        className="btn-ghost"
        style={{ paddingLeft: 0, marginBottom: 18 }}
        onClick={() => navigate('/dashboard')}
      >
        ← Back to dashboard
      </button>
      <div style={{ fontSize: 12, letterSpacing: '0.12em', textTransform: 'uppercase', color: 'var(--color-brass)', fontWeight: 600, marginBottom: 10 }}>
        {eyebrow}
      </div>
      <h1 style={{ fontSize: 34, marginBottom: 10 }}>{title}</h1>
      <p style={{ fontSize: 15 }}>{tagline}</p>
    </div>
  );
}
