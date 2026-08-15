import React, { useState } from 'react';

export default function CopyButton({ text }) {
  const [copied, setCopied] = useState(false);

  const handleCopy = async () => {
    if (!text) return;
    try {
      await navigator.clipboard.writeText(text);
      setCopied(true);
      setTimeout(() => setCopied(false), 1500);
    } catch {
      // Clipboard API may be unavailable (e.g. insecure context) - fail silently.
    }
  };

  return (
    <button type="button" className="btn-ghost" onClick={handleCopy} disabled={!text}>
      {copied ? 'Copied ✓' : 'Copy'}
    </button>
  );
}
