package com.cryptovault.service;

import com.cryptovault.dto.rsa.RsaKeyPairResponse;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA asymmetric encryption service.
 * Uses Java's built-in KeyPairGenerator and Cipher APIs (JCA) - no manual
 * mathematical implementation of RSA is performed here.
 */
@Service
public class RsaService {

    private static final String ALGORITHM = "RSA";
    // OAEP padding is the modern, secure choice for RSA encryption.
    private static final String TRANSFORMATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    private static final int KEY_SIZE = 2048;

    // RSA-2048 with OAEP-SHA256 can safely encrypt at most ~190 bytes in one block.
    private static final int MAX_PLAINTEXT_BYTES = 190;

    public RsaKeyPairResponse generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM);
            generator.initialize(KEY_SIZE);
            KeyPair keyPair = generator.generateKeyPair();

            String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

            return new RsaKeyPairResponse(publicKey, privateKey, KEY_SIZE);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("RSA algorithm not available: " + e.getMessage(), e);
        }
    }

    public String encrypt(String plaintext, String publicKeyBase64) {
        if (plaintext == null || plaintext.isBlank()) {
            throw new IllegalArgumentException("Plaintext must not be empty");
        }
        byte[] plainBytes = plaintext.getBytes();
        if (plainBytes.length > MAX_PLAINTEXT_BYTES) {
            throw new IllegalArgumentException(
                    "Plaintext too long for RSA-2048 with OAEP padding. Maximum is "
                            + MAX_PLAINTEXT_BYTES + " bytes; RSA is designed for small payloads, not bulk data.");
        }

        try {
            PublicKey publicKey = decodePublicKey(publicKeyBase64);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypted = cipher.doFinal(plainBytes);
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new IllegalArgumentException("RSA encryption failed: " + e.getMessage(), e);
        }
    }

    public String decrypt(String ciphertextBase64, String privateKeyBase64) {
        if (ciphertextBase64 == null || ciphertextBase64.isBlank()) {
            throw new IllegalArgumentException("Ciphertext must not be empty");
        }
        try {
            PrivateKey privateKey = decodePrivateKey(privateKeyBase64);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decoded = Base64.getDecoder().decode(ciphertextBase64);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted);
        } catch (javax.crypto.BadPaddingException e) {
            // This is the expected failure when the private key does not match the public
            // key that produced the ciphertext (or the ciphertext was tampered with).
            throw new IllegalArgumentException(
                    "Decryption failed: the private key does not match the key pair used to encrypt this ciphertext.", e);
        } catch (Exception e) {
            throw new IllegalArgumentException("RSA decryption failed: " + e.getMessage(), e);
        }
    }

    private PublicKey decodePublicKey(String base64Key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
        return factory.generatePublic(spec);
    }

    private PrivateKey decodePrivateKey(String base64Key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM);
        return factory.generatePrivate(spec);
    }
}