import React, { useState } from 'react';
import ModuleHeader from '../components/ModuleHeader';
import DataField from '../components/DataField';
import StatusBanner from '../components/StatusBanner';
import { processDhMessages } from '../api/dhApi';

export default function DhModule() {
  const [aliceMessage, setAliceMessage] = useState('');
  const [bobMessage, setBobMessage] = useState('');
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleProcess = async () => {
    if (!aliceMessage.trim() || !bobMessage.trim()) {
      setError('Both Alice and Bob must enter a message.');
      return;
    }

    setError('');
    setResult(null);
    setLoading(true);

    try {
      const data = await processDhMessages({
        aliceMessage,
        bobMessage,
      });

      setResult(data);
    } catch (e) {
      setError(
        e.response?.data?.message ||
        e.message ||
        'Failed to process messages.'
      );
    } finally {
      setLoading(false);
    }
  };

  const handleClear = () => {
    setAliceMessage('');
    setBobMessage('');
    setResult(null);
    setError('');
  };

  return (
    <div
      className="container"
      style={{
        paddingTop: 40,
        paddingBottom: 64,
        maxWidth: 960
      }}
    >
      <ModuleHeader
        eyebrow="Key Exchange"
        title="Diffie–Hellman"
        tagline="Alice and Bob exchange messages. Diffie–Hellman internally establishes a shared secret which is then used to process their messages."
      />

      <div
        className="card"
        style={{
          marginBottom: 20
        }}
      >
        <div
          style={{
            display: 'grid',
            gridTemplateColumns: '1fr 1fr',
            gap: 24
          }}
        >
          {/* Alice */}
          <div>
            <div
              style={{
                display: 'flex',
                alignItems: 'center',
                gap: 8,
                marginBottom: 12
              }}
            >
              <div
                style={{
                  width: 8,
                  height: 8,
                  borderRadius: '50%',
                  backgroundColor: 'var(--color-brass)'
                }}
              />

              <span
                style={{
                  fontFamily: 'var(--font-display)',
                  fontWeight: 600,
                  fontSize: 15
                }}
              >
                Alice
              </span>
            </div>

            <label className="field-label">
              Message
            </label>

            <textarea
              value={aliceMessage}
              onChange={(e) => setAliceMessage(e.target.value)}
              placeholder="Enter Alice's message"
              rows={5}
              style={{
                width: '100%',
                resize: 'vertical'
              }}
            />
          </div>

          {/* Bob */}
          <div>
            <div
              style={{
                display: 'flex',
                alignItems: 'center',
                gap: 8,
                marginBottom: 12
              }}
            >
              <div
                style={{
                  width: 8,
                  height: 8,
                  borderRadius: '50%',
                  backgroundColor: 'var(--color-teal)'
                }}
              />

              <span
                style={{
                  fontFamily: 'var(--font-display)',
                  fontWeight: 600,
                  fontSize: 15
                }}
              >
                Bob
              </span>
            </div>

            <label className="field-label">
              Message
            </label>

            <textarea
              value={bobMessage}
              onChange={(e) => setBobMessage(e.target.value)}
              placeholder="Enter Bob's message"
              rows={5}
              style={{
                width: '100%',
                resize: 'vertical'
              }}
            />
          </div>
        </div>

        <div
          style={{
            display: 'flex',
            justifyContent: 'center',
            marginTop: 24
          }}
        >
          <button
            className="btn btn-primary"
            onClick={handleProcess}
            disabled={loading}
          >
            {loading ? 'Processing…' : 'Process Messages'}
          </button>
        </div>
      </div>

      {result && (
        <>
          <div
            className="card"
            style={{
              marginBottom: 20
            }}
          >
            <div
              className="field-label"
              style={{ marginBottom: 12 }}
            >
              Diffie–Hellman Shared Secret
            </div>

            <DataField
              label="Shared Secret"
              value={result.sharedSecret}
            />
          </div>

          <div
            className="card"
            style={{
              marginBottom: 20
            }}
          >
            <div
              className="field-label"
              style={{ marginBottom: 18 }}
            >
              Final Output
            </div>

            <div
              style={{
                display: 'grid',
                gridTemplateColumns: '1fr 1fr',
                gap: 24
              }}
            >
              <DataField
                label="Alice's Encrypted Message"
                value={result.encryptedAliceMessage}
              />

              <DataField
                label="Bob's Encrypted Message"
                value={result.encryptedBobMessage}
              />
            </div>
          </div>

          <StatusBanner
            type={result.match ? 'success' : 'error'}
          >
            {result.match
              ? 'Shared secret matched ✓'
              : 'Shared secret mismatch ✗'}
          </StatusBanner>
        </>
      )}

      {error && (
        <StatusBanner type="error">
          {error}
        </StatusBanner>
      )}

      <div style={{ marginTop: 24 }}>
        <button
          className="btn-ghost"
          onClick={handleClear}
        >
          Clear / Reset module
        </button>
      </div>
    </div>
  );
}