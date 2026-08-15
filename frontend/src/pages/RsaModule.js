import React, { useState } from 'react';
import ModuleHeader from '../components/ModuleHeader';
import DataField from '../components/DataField';
import StatusBanner from '../components/StatusBanner';
import { generateRsaKeys, rsaEncrypt, rsaDecrypt } from '../api/rsaApi';

export default function RsaModule() {
  const [plaintext, setPlaintext] = useState('Hello world!!');
  const [keys, setKeys] = useState(null);
  const [ciphertext, setCiphertext] = useState('');
  const [decrypted, setDecrypted] = useState('');
  const [loading, setLoading] = useState('');
  const [error, setError] = useState('');

  const resetDownstream = () => {
    setCiphertext('');
    setDecrypted('');
  };

  const handleGenerateKeys = async () => {
    setError('');
    setLoading('keys');
    try {
      const data = await generateRsaKeys();
      setKeys(data);
      resetDownstream();
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading('');
    }
  };

  const handleEncrypt = async () => {
    setError('');
    setLoading('encrypt');
    try {
      const data = await rsaEncrypt(plaintext, keys.publicKey);
      setCiphertext(data.ciphertext);
      setDecrypted('');
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading('');
    }
  };

  const handleDecrypt = async () => {
    setError('');
    setLoading('decrypt');
    try {
      const data = await rsaDecrypt(ciphertext, keys.privateKey);
      setDecrypted(data.plaintext);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading('');
    }
  };

  const handleClear = () => {
    setKeys(null);
    setCiphertext('');
    setDecrypted('');
    setError('');
  };

  return (
    <div className="container" style={{ paddingTop: 40, paddingBottom: 64, maxWidth: 900 }}>
      <ModuleHeader
        eyebrow="Asymmetric Encryption"
        title="RSA · Public Key / Private Key"
        tagline="Encrypt with the public key, decrypt with the private key. RSA-2048 with OAEP-SHA256 padding, generated live by Java's KeyPairGenerator and Cipher APIs."
      />

      <div className="card" style={{ marginBottom: 20 }}>
        <label className="field-label" htmlFor="rsa-plaintext">Step 1 · Plaintext</label>
        <textarea
          id="rsa-plaintext"
          className="textarea-input"
          value={plaintext}
          onChange={(e) => { setPlaintext(e.target.value); resetDownstream(); }}
          placeholder="Enter a short message (RSA is not meant for bulk data)..."
        />
        <p style={{ fontSize: 12, marginTop: 8 }}>
          Keep it under ~190 bytes — RSA-2048 with OAEP padding can only encrypt small payloads directly.
        </p>
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
          <span className="field-label" style={{ marginBottom: 0 }}>Step 3 · Encrypt</span>
          <button className="btn btn-secondary" onClick={handleEncrypt} disabled={!keys || !plaintext || loading === 'encrypt'}>
            {loading === 'encrypt' ? 'Encrypting…' : 'Encrypt'}
          </button>
        </div>
        <DataField label="Ciphertext (Base64)" value={ciphertext} placeholder="Generate keys, then encrypt" />
      </div>

      <div className="card" style={{ marginBottom: 20 }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 16 }}>
          <span className="field-label" style={{ marginBottom: 0 }}>Step 4 · Decrypt</span>
          <button className="btn btn-secondary" onClick={handleDecrypt} disabled={!ciphertext || loading === 'decrypt'}>
            {loading === 'decrypt' ? 'Decrypting…' : 'Decrypt'}
          </button>
        </div>
        <DataField label="Recovered Plaintext" value={decrypted} placeholder="Encrypt first, then decrypt" />
      </div>

      {decrypted && (
        <StatusBanner type={decrypted === plaintext ? 'success' : 'error'}>
          {decrypted === plaintext
            ? 'Decrypted text matches the original plaintext'
            : 'Decrypted text does not match the original plaintext'}
        </StatusBanner>
      )}
      {error && <StatusBanner type="error">{error}</StatusBanner>}

      <div style={{ marginTop: 24 }}>
        <button className="btn-ghost" onClick={handleClear}>Clear / Reset module</button>
      </div>
    </div>
  );
}
