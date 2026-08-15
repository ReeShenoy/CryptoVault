import React from 'react';

export default function StatusBanner({ type = 'success', children }) {
  if (!children) return null;
  const className = type === 'error' ? 'status-banner status-error' : 'status-banner status-success';
  const icon = type === 'error' ? '✗' : '✓';
  return (
    <div className={className}>
      <span>{icon}</span>
      <span>{children}</span>
    </div>
  );
}
