import React from 'react';
import { useNavigate } from 'react-router-dom';

const modules = [
  {
    id: 'rsa',
    tag: 'Asymmetric Encryption',
    title: 'RSA',
    subtitle: 'Public Key / Private Key',
    description:
      'Encrypt a message with a public key and decrypt it with the matching private key — the two keys are mathematically linked but computationally infeasible to derive from one another.',
    flow: 'Plaintext → Encrypt → Ciphertext → Decrypt → Plaintext',
    route: '/rsa',
    accent: 'var(--color-brass)',
  },
  {
    id: 'dh',
    tag: 'Key Exchange',
    title: 'Diffie–Hellman',
    subtitle: 'Shared Secret',
    description:
      'Watch Alice and Bob each generate their own key pair, exchange only public keys, and independently arrive at the exact same shared secret without ever transmitting it.',
    flow: 'Alice Keys + Bob Keys → Exchange → Shared Secret → Match',
    route: '/dh',
    accent: 'var(--color-teal)',
  },
  {
    id: 'dsa',
    tag: 'Digital Signature',
    title: 'DSA',
    subtitle: 'Sign / Verify',
    description:
      'Sign a message with a private key to produce a digital signature, then verify it with the public key — tampering with the message afterward invalidates the signature.',
    flow: 'Message → Sign → Signature → Verify → Valid / Invalid',
    route: '/dsa',
    accent: '#8b7fd6',
  },
];

export default function DashboardPage() {
  const navigate = useNavigate();

  return (
    <div className="container" style={{ paddingTop: 48, paddingBottom: 64 }}>
      <div style={{ marginBottom: 44 }}>
        <div style={{ fontSize: 12, letterSpacing: '0.12em', textTransform: 'uppercase', color: 'var(--color-brass)', fontWeight: 600, marginBottom: 10 }}>
          Cryptography Dashboard
        </div>
        <h1 style={{ fontSize: 32, marginBottom: 10 }}>Choose an algorithm to demonstrate</h1>
        <p style={{ fontSize: 15, maxWidth: 620 }}>
          Each module talks to the live Spring Boot backend and performs real cryptographic
          operations using Java's built-in security APIs.
        </p>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))', gap: 22 }}>
        {modules.map((m) => (
          <div key={m.id} className="card" style={{ display: 'flex', flexDirection: 'column' }}>
            <div style={{ width: 34, height: 4, borderRadius: 2, backgroundColor: m.accent, marginBottom: 18 }} />
            <div style={{ fontSize: 11, letterSpacing: '0.1em', textTransform: 'uppercase', color: 'var(--color-text-muted)', fontWeight: 600, marginBottom: 8 }}>
              {m.tag}
            </div>
            <h2 style={{ fontSize: 24, marginBottom: 2 }}>{m.title}</h2>
            <div className="mono" style={{ fontSize: 12.5, color: m.accent, marginBottom: 14 }}>{m.subtitle}</div>
            <p style={{ fontSize: 14, marginBottom: 18, flex: 1 }}>{m.description}</p>
            <div className="mono" style={{ fontSize: 11, color: 'var(--color-text-muted)', marginBottom: 20, lineHeight: 1.7 }}>
              {m.flow}
            </div>
            <button className="btn btn-secondary" onClick={() => navigate(m.route)}>
              Open {m.title} →
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}
