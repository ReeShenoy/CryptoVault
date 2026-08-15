import React from 'react';
import { useNavigate } from 'react-router-dom';

export default function LandingPage() {
  const navigate = useNavigate();

  return (
    <div className="container" style={{ minHeight: '100vh', display: 'flex', flexDirection: 'column' }}>
      <header style={{ padding: '32px 0', display: 'flex', alignItems: 'center', gap: 10 }}>
        <div style={vaultMark} />
        <span style={{ fontFamily: 'var(--font-display)', fontWeight: 600, letterSpacing: '0.02em' }}>
          CryptoVault
        </span>
      </header>

      <main style={{ flex: 1, display: 'grid', gridTemplateColumns: '1.1fr 0.9fr', alignItems: 'center', gap: 48 }}>
        <div>
          <div style={{ fontSize: 12, letterSpacing: '0.14em', textTransform: 'uppercase', color: 'var(--color-brass)', fontWeight: 600, marginBottom: 18 }}>
            Cryptography Demonstration System
          </div>
          <h1 style={{ fontSize: 48, lineHeight: 1.08, marginBottom: 22 }}>
            Three ciphers.<br />One vault door.
          </h1>
          <p style={{ fontSize: 17, maxWidth: 480, marginBottom: 32 }}>
            CryptoVault runs real RSA encryption, Diffie-Hellman key exchange, and DSA digital
            signatures against Java's own cryptography engine — every key, ciphertext, and
            signature on screen is generated live by the backend, not staged.
          </p>
          <button className="btn btn-primary" onClick={() => navigate('/dashboard')}>
            Explore CryptoVault →
          </button>

          <div style={{ display: 'flex', gap: 28, marginTop: 44 }}>
            <Stat label="RSA-2048" desc="Asymmetric encryption" />
            <Stat label="DH-2048" desc="Key exchange" />
            <Stat label="SHA256withDSA" desc="Digital signatures" />
          </div>
        </div>

        <div style={{ display: 'flex', justifyContent: 'center' }}>
          <VaultDial />
        </div>
      </main>

      <footer style={{ padding: '24px 0', color: 'var(--color-text-muted)', fontSize: 13 }}>
        Built with the Java Cryptography Architecture (JCA) · Spring Boot · React
      </footer>
    </div>
  );
}

function Stat({ label, desc }) {
  return (
    <div>
      <div className="mono" style={{ color: 'var(--color-teal)', fontSize: 14, fontWeight: 600 }}>{label}</div>
      <div style={{ fontSize: 12.5, color: 'var(--color-text-muted)', marginTop: 2 }}>{desc}</div>
    </div>
  );
}

const vaultMark = {
  width: 22,
  height: 22,
  borderRadius: '50%',
  border: '2.5px solid var(--color-brass)',
  position: 'relative',
  boxShadow: 'inset 0 0 0 4px var(--color-bg)',
};

// A hand-built concentric "vault dial" - the page's signature element.
function VaultDial() {
  const ticks = Array.from({ length: 24 });
  return (
    <svg width="360" height="360" viewBox="0 0 360 360" role="img" aria-label="Vault combination dial illustration">
      <circle cx="180" cy="180" r="172" fill="none" stroke="var(--color-border)" strokeWidth="1.5" />
      <circle cx="180" cy="180" r="150" fill="var(--color-surface)" stroke="var(--color-border)" strokeWidth="1" />
      {ticks.map((_, i) => {
        const angle = (i / ticks.length) * 2 * Math.PI;
        const isMajor = i % 6 === 0;
        const r1 = isMajor ? 130 : 138;
        const r2 = 150;
        const x1 = 180 + r1 * Math.cos(angle);
        const y1 = 180 + r1 * Math.sin(angle);
        const x2 = 180 + r2 * Math.cos(angle);
        const y2 = 180 + r2 * Math.sin(angle);
        return (
          <line
            key={i}
            x1={x1} y1={y1} x2={x2} y2={y2}
            stroke={isMajor ? 'var(--color-brass)' : 'var(--color-text-muted)'}
            strokeWidth={isMajor ? 2 : 1}
            opacity={isMajor ? 0.9 : 0.4}
          />
        );
      })}
      <circle cx="180" cy="180" r="108" fill="var(--color-bg)" stroke="var(--color-border)" strokeWidth="1" />
      <circle cx="180" cy="180" r="72" fill="none" stroke="var(--color-brass)" strokeWidth="1" opacity="0.5" strokeDasharray="2 6" />

      {/* Center lock body */}
      <rect x="150" y="168" width="60" height="46" rx="8" fill="var(--color-surface-raised)" stroke="var(--color-brass)" strokeWidth="1.5" />
      <path d="M162 168 v-14 a18 18 0 0 1 36 0 v14" fill="none" stroke="var(--color-brass)" strokeWidth="5" strokeLinecap="round" />
      <circle cx="180" cy="188" r="6" fill="var(--color-brass-bright)" />
      <line x1="180" y1="188" x2="180" y2="200" stroke="var(--color-brass-bright)" strokeWidth="3" strokeLinecap="round" />

      <text x="180" y="70" textAnchor="middle" className="mono" fill="var(--color-text-muted)" fontSize="11" letterSpacing="2">
        RSA · DH · DSA
      </text>
    </svg>
  );
}
