import React, { useState } from 'react';
import ModuleHeader from '../components/ModuleHeader';
import DataField from '../components/DataField';
import StatusBanner from '../components/StatusBanner';
import { generateDsaKeys, dsaSign, dsaVerify } from '../api/dsaApi';

export default function DsaModule() {
  const [message, setMessage] = useState('Hello world!!');
  const [signedMessage, setSignedMessage] = useState(''); // message the signature was actually created for
  const [keys, setKeys] = useState(null);
  const [signature, setSignature] = useState('');
  const [verifyResult, setVerifyResult] = useState(null); // true | false | null
  const [loading, setLoading] = useState('');
  const [error, setError] = useState('');

  const handleGenerateKeys = async () => {
    setError('');
    setLoading('keys');
    try {
      const data = await generateDsaKeys();
      setKeys(data);
      setSignature('');
      setVerifyResult(null);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading('');
    }
  };

  const handleSign = async () => {
    setError('');
    setLoading('sign');
    try {
      const data = await dsaSign(message, keys.privateKey);
      setSignature(data.signature);
      setSignedMessage(message);
      setVerifyResult(null);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading('');
    }
  };

  const handleVerify = async () => {
    setError('');
    setLoading('verify');
    try {
      const data = await dsaVerify(message, signature, keys.publicKey);
      setVerifyResult(data.valid);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading('');
    }
  };

  const handleClear = () => {
    setKeys(null);
    setSignature('');
    setSignedMessage('');
    setVerifyResult(null);
    setError('');
  };

  const messageWasTampered = signature && message !== signedMessage;

  return (
    <div className="container" style={{ paddingTop: 40, paddingBottom: 64, maxWidth: 900 }}>
      <ModuleHeader
        eyebrow="Digital Signature"
        title="DSA · Sign / Verify"
        tagline="Sign a message with a private key to prove authorship, then verify it with the public key. Edit the message after signing to see verification fail — that's the signature doing its job."
      />

      <div className="card" style={{ marginBottom: 20 }}>
        <label className="field-label" htmlFor="dsa-message">Step 1 · Message</label>
        <textarea
          id="dsa-message"
          className="textarea-input"
          value={message}
          onChange={(e) => { setMessage(e.target.value); setVerifyResult(null); }}
          placeholder="Enter a message to sign..."
        />
        {messageWasTampered && (
          <p style={{ fontSize: 12, marginTop: 8, color: 'var(--color-red)' }}>
            The message has changed since it was signed. Verifying now will demonstrate an invalid signature.
          </p>
        )}
      </div>

      <div className="card" style={{ marginBottom: 20 }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 16 }}>
          <span className="field-label" style={{ marginBottom: 0 }}>Step 2 · Key Pair</span>
          <button className="btn btn-primary" onClick={handleGenerateKeys} disabled={loading === 'keys'}>
            {loading === 'keys' ? 'Generating…' : 'Generate Keys'}
          </button>
        </div>
        <DataField label="Public Key (Base64, X.509)" value={keys?.publicKey} />
        <DataField label="Private Key (Base64, PKCS#8)" value={keys?.privateKey} />
      </div>

      <div className="card" style={{ marginBottom: 20 }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 16 }}>
          <span className="field-label" style={{ marginBottom: 0 }}>Step 3 · Sign message</span>
          <button className="btn btn-secondary" onClick={handleSign} disabled={!keys || !message || loading === 'sign'}>
            {loading === 'sign' ? 'Signing…' : 'Sign Message'}
          </button>
        </div>
        <DataField label="Digital Signature (Base64)" value={signature} placeholder="Generate keys, then sign" />
      </div>

      <div className="card" style={{ marginBottom: 20 }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 16 }}>
          <span className="field-label" style={{ marginBottom: 0 }}>Step 4 · Verify signature</span>
          <button className="btn btn-secondary" onClick={handleVerify} disabled={!signature || loading === 'verify'}>
            {loading === 'verify' ? 'Verifying…' : 'Verify Signature'}
          </button>
        </div>
        <p style={{ fontSize: 13 }}>
          Try editing the message in Step 1 after signing, then verify again — the signature was
          computed over the original text, so it will no longer match.
        </p>
      </div>

      {verifyResult !== null && (
        <StatusBanner type={verifyResult ? 'success' : 'error'}>
          {verifyResult ? 'Signature Valid ✓' : 'Signature Invalid ✗'}
        </StatusBanner>
      )}
      {error && <StatusBanner type="error">{error}</StatusBanner>}

      <div style={{ marginTop: 24 }}>
        <button className="btn-ghost" onClick={handleClear}>Clear / Reset module</button>
      </div>
    </div>
  );
}
