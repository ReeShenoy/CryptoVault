import React from 'react';
import CopyButton from './CopyButton';

export default function DataField({ label, value, placeholder = 'Not generated yet' }) {
  return (
    <div style={{ marginBottom: 18 }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 8 }}>
        <span className="field-label" style={{ marginBottom: 0 }}>{label}</span>
        {value ? <CopyButton text={value} /> : null}
      </div>
      <div className="data-block">
        {value || placeholder}
      </div>
    </div>
  );
}
