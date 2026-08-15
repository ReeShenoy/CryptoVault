package com.cryptovault.service;

import com.cryptovault.dto.dsa.DsaKeyPairResponse;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * DSA digital signature service.
 * Uses Java's built-in KeyPairGenerator and Signature APIs (JCA).
 */
@Service
public class DsaService {

    private static final String KEY_ALGORITHM = "DSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withDSA";
    private static final int KEY_SIZE = 2048;

    public DsaKeyPairResponse generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            generator.initialize(KEY_SIZE);
            KeyPair keyPair = generator.generateKeyPair();

            String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

            return new DsaKeyPairResponse(publicKey, privateKey);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("DSA algorithm not available: " + e.getMessage(), e);
        }
    }

    public String sign(String message, String privateKeyBase64) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message must not be empty");
        }
        try {
            PrivateKey privateKey = decodePrivateKey(privateKeyBase64);
            Signature signer = Signature.getInstance(SIGNATURE_ALGORITHM);
            signer.initSign(privateKey);
            signer.update(message.getBytes());
            byte[] signatureBytes = signer.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (Exception e) {
            throw new IllegalArgumentException("DSA signing failed: " + e.getMessage(), e);
        }
    }

    public boolean verify(String message, String signatureBase64, String publicKeyBase64) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message must not be empty");
        }
        if (signatureBase64 == null || signatureBase64.isBlank()) {
            throw new IllegalArgumentException("Signature must not be empty");
        }
        try {
            PublicKey publicKey = decodePublicKey(publicKeyBase64);
            Signature verifier = Signature.getInstance(SIGNATURE_ALGORITHM);
            verifier.initVerify(publicKey);
            verifier.update(message.getBytes());
            byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);
            return verifier.verify(signatureBytes);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            // A genuinely broken key/algorithm setup is a real error, not just an invalid signature.
            throw new IllegalArgumentException("DSA verification failed: " + e.getMessage(), e);
        } catch (Exception e) {
            // Malformed/tampered signature bytes (e.g. bad Base64 or bad ASN.1 encoding) simply
            // mean the signature does not verify - report it as "invalid" rather than a 500 error.
            return false;
        }
    }

    private PublicKey decodePublicKey(String base64Key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
        return factory.generatePublic(spec);
    }

    private PrivateKey decodePrivateKey(String base64Key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
        return factory.generatePrivate(spec);
    }
}
